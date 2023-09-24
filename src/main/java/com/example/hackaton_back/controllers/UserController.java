package com.example.hackaton_back.controllers;


import com.example.hackaton_back.payload.request.CreateUserRequest;
import com.example.hackaton_back.services.PhotoService;
import com.example.hackaton_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PhotoService photoService;

    @Autowired
    public UserController(UserService userService, PhotoService photoService) {
        this.userService = userService;
        this.photoService = photoService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody CreateUserRequest createUserRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.createUser(createUserRequest);
            response.put("message", "Регистрация успешна");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/set-identification")
    public ResponseEntity<Map<String,String>> setIdentification(@RequestParam String face,
                                                                @RequestParam String passport,
                                                                @RequestParam String email){
        Map<String,String> response = new HashMap<>();
        try{
            userService.setIdentification(face, passport,email);
            response.put("message","Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (RuntimeException e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/signIn")
    public ResponseEntity<Map<String, String>> signIn(@RequestBody CreateUserRequest createUserRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.signIn(createUserRequest);
            return ResponseEntity.ok(null);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/photo")
    public ResponseEntity<Map<String,String>> photo(@RequestParam MultipartFile photo) {

        Map<String,String> response = new HashMap<>();
        try {
            photoService.savePetitionPhoto(photo);
            return ResponseEntity.ok(null);
        } catch (IOException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
