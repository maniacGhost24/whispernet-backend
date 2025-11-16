package com.maniac.whispernet.service;


import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {
    private final Map<String, String> sessionToUser = new ConcurrentHashMap<>();

    public void register(String sessionId, String username){
        sessionToUser.put(sessionId, username);
    }

    public String getUser(String sessionId){
        return sessionToUser.get(sessionId);
    }

    public void remove(String sessionId){
        sessionToUser.remove(sessionId);
    }
}
