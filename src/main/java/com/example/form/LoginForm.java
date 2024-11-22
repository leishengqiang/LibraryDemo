package com.example.form;

import lombok.Data;

@Data
public class LoginForm {
    private String username;
    private String password;
    private Integer type;
}
