package com.example.hackaton_back.entities.petitions;

import com.example.hackaton_back.entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Petition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User creator;
    private String ruTitle;
    private String kgTitle;
    private String ruDescription;
    private String kgDescription;
    private String photo;

    @ManyToMany
    private List<User> followers;

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

    public void removeFollower(User user) {
        this.getFollowers().remove(user);
    }

    public void addFollower(User user) {
        this.getFollowers().add(user);
    }
}
