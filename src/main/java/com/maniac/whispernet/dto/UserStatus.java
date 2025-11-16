package com.maniac.whispernet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatus {
    public enum Status {ONLINE, OFFLINE}

    private String username;
    private Status status;
}
