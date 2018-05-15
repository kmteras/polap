package com.eucolus.poll.services;

import com.eucolus.poll.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
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

        Collections.sort(objects, (o1, o2) -> {
            Long l1 = (Long)o1[1];
            Long l2 = (Long)o2[1];
            return l2.compareTo(l1);
        });

        final int TOP = 5;

        // Look no further if you want to sleep
        if(objects.size() > TOP) {
            Object[] o6 = objects.get(TOP);
            o6[0] = "Other";
            Long count = (Long) o6[1];
            for (int i = TOP + 1; i < objects.size(); i++) {
                count += (Long) objects.get(i)[1];
            }
            o6[1] = count;
            objects.set(TOP, o6);

            while(objects.size() > TOP + 1) {
                objects.remove(TOP + 1);
            }
        }

        return objects;
    }
}
