package com.example.hackaton_back.services.facades;

import com.example.hackaton_back.dao.CommentDTO;
import com.example.hackaton_back.entities.User;
import com.example.hackaton_back.entities.petitions.PetitionComment;
import com.example.hackaton_back.repositories.petitions.PetitionCommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentFacade {

    private final PetitionCommentLikeRepository petitionCommentLikeRepository;

    @Autowired
    public CommentFacade(PetitionCommentLikeRepository petitionCommentLikeRepository) {
        this.petitionCommentLikeRepository = petitionCommentLikeRepository;
    }

    public CommentDTO petitionCommentToCommentDTO(PetitionComment comment, User user){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment(comment.getComment());
        commentDTO.setUser(user);
        commentDTO.setCommentId(comment.getId());
        commentDTO.setLikeCount(petitionCommentLikeRepository.countByCommentAndIsLike(comment,true).orElse(0));
        commentDTO.setDislikeCount(petitionCommentLikeRepository.countByCommentAndIsLike(comment,false).orElse(0));
        commentDTO.setIsMineLike(petitionCommentLikeRepository.findByUserAndComment(user,comment).isPresent());
        commentDTO.setIsMine(comment.getUser().equals(user));
        return commentDTO;
    }

    public List<CommentDTO> commentsToCommentsDTOs(List<PetitionComment> comments, User user){
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for(PetitionComment comment : comments)
            commentDTOS.add(petitionCommentToCommentDTO(comment, user));
        return commentDTOS;
    }
}
