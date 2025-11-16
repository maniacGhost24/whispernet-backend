package com.maniac.whispernet.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserDirectoryService {
    private final SimpMessagingTemplate msg;
    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    public UserDirectoryService(SimpMessagingTemplate msg){
        this.msg = msg;
    }

    public void addUser(String username){
        onlineUsers.add(username);
        broadcast();
    }

    public void removeUser(String username){
        onlineUsers.remove(username);
        broadcast();
    }

    public Set<String> getOnlineUsers(){
        return onlineUsers;
    }

    private void broadcast(){
        msg.convertAndSend("/topic/online-users",
                onlineUsers.toArray(String[]::new));
    }
}
