package com.maniac.whispernet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    public enum Type{
        DIRECT_MESSAGE,     // encrypted ciphertext
        USER_JOIN,
        USER_LEAVE
    }

    private Type type;
    private String sender;      // username of sender.
    private String receiver;
    private String content;     // message as cipher text
}
