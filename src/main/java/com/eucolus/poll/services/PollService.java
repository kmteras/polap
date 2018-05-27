package com.eucolus.poll.services;

import com.eucolus.poll.entities.Poll;
import com.eucolus.poll.entities.PollSession;
import com.eucolus.poll.entities.PollUser;
import com.eucolus.poll.repositories.PollSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PollService {
    @Autowired
    private PollSessionRepository pollSessionRepository;

    private Map<String, PollSession> sessions;

    public PollService() {
        sessions = new HashMap<>();
    }

    public PollSession findSession(String sessionCode) {
        return sessions.get(sessionCode);
    }

    public PollSession startSession(Poll poll, PollUser user) {
        PollSession pollSession = new PollSession();
        String code = generateCode();
        pollSession.setCode(code);
        pollSession.setStartTime(new Timestamp(System.currentTimeMillis()));
        pollSession.setPoll(poll);
        pollSession.setHost(user);
        sessions.put(code, pollSession);
        pollSessionRepository.save(pollSession);
        return pollSession;
    }

    public void endSession(PollSession pollSession) {
        pollSession.setEndTime(new Timestamp(System.currentTimeMillis()));

        pollSessionRepository.save(pollSession);
        sessions.remove(pollSession.getCode());
    }

    public List<PollSession> getSessions(PollUser host) {
        List<PollSession> sessionList = new ArrayList<>();
        for(PollSession session : sessions.values()) {
            if(session.getHost().equals(host)) {
                sessionList.add(session);
            }
        }
        return sessionList;
    }

    public PollSession getSavedSession(String sessionCode) {
        return pollSessionRepository.findOne(findSession(sessionCode).getId());
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public Poll getPoll(String sessionCode) {
        return findSession(sessionCode).getPoll();
    }

    private String generateCode() {
        StringBuilder code = new StringBuilder();

        for(int i = 0; i < 6; i++) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            code.append(ALPHA_NUMERIC_STRING.charAt(character));
        }

        if(!sessions.containsKey(code.toString())) {
            return code.toString();
        }
        else {
            return generateCode();
        }
    }
}
