package com.senti.bert.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginDto {
    @NotNull
    private String userId;

    @NotNull
    private String password;
}
