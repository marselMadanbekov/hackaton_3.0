package com.example.hackaton_back.dao;

import com.example.hackaton_back.entities.User;
import lombok.Data;

@Data
public class CommentDTO {
    private String comment;
    private User user;
    private Long commentId;
    private Integer likeCount;
    private Integer dislikeCount;
    private Boolean isMineLike;
    private Boolean isMine;
}
