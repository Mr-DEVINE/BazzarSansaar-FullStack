package com.example.BazzarSansaar.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}

