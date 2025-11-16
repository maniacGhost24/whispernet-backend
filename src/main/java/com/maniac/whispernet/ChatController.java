package com.maniac.whispernet;


import com.maniac.whispernet.dto.ChatMessage;
import com.maniac.whispernet.dto.TypingEvent;
import com.maniac.whispernet.service.SessionService;
import com.maniac.whispernet.service.UserDirectoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate msg;
    private final UserDirectoryService users;
    private final SessionService sessions;

//    USER JOINS

    @MessageMapping("/user.join")
    public void joinUser(@Header("simpSessionId") String sessionId,
                         @Payload String username){
        sessions.register(sessionId, username);
        users.addUser(username);

//        NOTIFY OTHERS
        ChatMessage joinMsg = new ChatMessage(
                ChatMessage.Type.USER_JOIN, username, null, null
        );
        msg.convertAndSend("/topic/public", joinMsg);

//        Send current online users to newly joined user.
        Set<String> onlineUsers = users.getOnlineUsers();
        msg.convertAndSend("/topic/private."+username+".users",onlineUsers.toArray(String[]::new));
    }

//    DIRECT MESSAGE (END-TO-END ENCRYPTION)
    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage m){

//      Server doesn't decrypt the message content that is sent from server is only cipher text from client
        msg.convertAndSend("/topic/private."+m.getReceiver(),m);
    }

//    TYPING INDICATOR
    @MessageMapping("/typing")
    public void typing(@Payload TypingEvent e){
        msg.convertAndSend("/topic/private."+e.getToUser()+".typing",e);
    }

//    USER DISCONNECT
    @EventListener
    public void disconnect(SessionDisconnectEvent event){
        String sessionId = event.getSessionId();
        String username = sessions.getUser(sessionId);


        if(username==null) return;

        sessions.remove(sessionId);
        users.removeUser(username);

        ChatMessage leaveMsg = new ChatMessage(
                ChatMessage.Type.USER_LEAVE, username, null, null
        );

        msg.convertAndSend("/topic/public",leaveMsg);
    }
}
