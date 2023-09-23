package com.example.hackaton_back.payload.request;


import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String password;
}
