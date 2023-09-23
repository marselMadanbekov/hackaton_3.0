package com.example.hackaton_back.entities;

import com.example.hackaton_back.entities.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String inn;
    private Role role;
    private Double rating;

    private Date createDate;
    private Date lastUpdate;

    @PrePersist
    private void onCreate(){
        this.createDate = Date.valueOf(LocalDate.now());
        lastUpdate = Date.valueOf(LocalDate.now());
    }

    public void updated(){
        this.lastUpdate = Date.valueOf(LocalDate.now());
    }
    public User(){}
    public User(Long id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
