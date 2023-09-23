package com.example.hackaton_back.services;

import com.example.hackaton_back.entities.User;
import com.example.hackaton_back.payload.request.CreateUserRequest;
import com.example.hackaton_back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public void createUser(CreateUserRequest createUserRequest) {
        try {
            User user = new User();
            user.setEmail(createUserRequest.getEmail());
            user.setPassword(createUserRequest.getPassword());
            userRepository.save(user);
        }catch (Exception e){
            throw new RuntimeException("Такой пользователь уже существует");
        }
    }

    public void signIn(CreateUserRequest createUserRequest){
        User user = getUserByEmail(createUserRequest.getEmail());
        if(!Objects.equals(user.getPassword(), createUserRequest.getPassword()))
            throw new RuntimeException("Неправильный пароль");
    }
}
