package com.maniac.whispernet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypingEvent {
    private String fromUser;
    private String toUser;
    private boolean typing;     // true = typing_start, false = typing_stop
}
