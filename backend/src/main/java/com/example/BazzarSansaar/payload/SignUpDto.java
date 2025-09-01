package com.example.BazzarSansaar.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpDto {
    private String username;
    private String email;
    private String password;
}

