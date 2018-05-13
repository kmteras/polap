package com.eucolus.poll.services;

import com.eucolus.poll.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    public List<Object[]> getBrowsers() {
        List<Object[]> browserRequestCount = requestRepository.getBrowserRequestsCount();

        HashMap<String, Long> boList = new HashMap<>();

        Pattern pattern = Pattern.compile(".+?(?=[\\s][0-9]|$)");

        for(Object[] o : browserRequestCount) {
            String name = (String)o[0];
            Long count = ((BigInteger)o[1]).longValue();
            Matcher matcher = pattern.matcher(name);
            if(matcher.find()) {
                String subName = matcher.group(0);
                boList.put(subName, boList.getOrDefault(subName, Long.valueOf(0)) + count);
            }
        }

        List<Object[]> objects = new ArrayList<>();
        for(String name : boList.keySet()) {
            Object[] entry = new Object[2];
            entry[0] = name;
            entry[1] = boList.get(name);
            objects.add(entry);
        }

        return objects;
    }
}
