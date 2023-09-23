package com.example.hackaton_back.services;


import com.example.hackaton_back.dao.CommentDTO;
import com.example.hackaton_back.dao.PetitionContent;
import com.example.hackaton_back.dao.PetitionDTO;
import com.example.hackaton_back.entities.petitions.Petition;
import com.example.hackaton_back.entities.petitions.PetitionComment;
import com.example.hackaton_back.entities.petitions.PetitionCommentLike;
import com.example.hackaton_back.entities.petitions.PetitionLike;
import com.example.hackaton_back.entities.User;
import com.example.hackaton_back.payload.request.CreatePetitionRequest;
import com.example.hackaton_back.repositories.petitions.PetitionCommentLikeRepository;
import com.example.hackaton_back.repositories.petitions.PetitionCommentRepository;
import com.example.hackaton_back.repositories.petitions.PetitionLikeRepository;
import com.example.hackaton_back.repositories.petitions.PetitionRepository;
import com.example.hackaton_back.repositories.UserRepository;
import com.example.hackaton_back.services.facades.CommentFacade;
import com.example.hackaton_back.services.facades.PetitionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PetitionService {
    private final PetitionRepository petitionRepository;
    private final PetitionLikeRepository petitionLikeRepository;
    private final PetitionCommentRepository petitionCommentRepository;
    private final PetitionCommentLikeRepository petitionCommentLikeRepository;
    private final PhotoService photoService;
    private final UserRepository userRepository;
    private final PetitionFacade petitionFacade;
    private final CommentFacade commentFacade;


    @Autowired
    public PetitionService(PetitionRepository petitionRepository, PetitionLikeRepository petitionLikeRepository, PetitionCommentRepository petitionCommentRepository, PetitionCommentLikeRepository petitionCommentLikeRepository, PhotoService photoService, UserRepository userRepository, PetitionFacade petitionFacade, CommentFacade commentFacade) {
        this.petitionRepository = petitionRepository;
        this.petitionLikeRepository = petitionLikeRepository;
        this.petitionCommentRepository = petitionCommentRepository;
        this.petitionCommentLikeRepository = petitionCommentLikeRepository;
        this.photoService = photoService;
        this.userRepository = userRepository;
        this.petitionFacade = petitionFacade;
        this.commentFacade = commentFacade;
    }

    public Petition createPetition(CreatePetitionRequest createPetitionRequest) {
        User user = getUserByEmail(createPetitionRequest.getEmail());
        Petition petition = new Petition();
        petition.setKgTitle(createPetitionRequest.getKgTitle());
        petition.setRuTitle(createPetitionRequest.getRuTitle());
        petition.setKgDescription(createPetitionRequest.getKgDescription());
        petition.setRuDescription(createPetitionRequest.getRuDescription());
        petition.setCreator(user);
        try {
            String photo = photoService.savePetitionPhoto(createPetitionRequest.getPhoto());
            petition.setPhoto(photo);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения фото : " + e.getMessage());
        }

        return petitionRepository.save(petition);
    }

    public List<PetitionDTO> getAllPetitions(String email) {
        User user = getUserByEmail(email);
        List<Petition> petitions = petitionRepository.findAll();
        return petitionFacade.mapPetitionsToPetitionDTOS(petitions, user);
    }

    public void likePetition(String email, Long petitionId, boolean isLike) {
        User user = getUserByEmail(email);
        Petition petition = getPetitionById(petitionId);
        PetitionLike petitionLike1 = getPetitionLikeByUserAndPetition(user, petition);

        if (petitionLike1 == null) {
            PetitionLike petitionLike = new PetitionLike();
            petitionLike.setPetition(petition);
            petitionLike.setUser(user);
            petitionLike.setIsLike(isLike);
            petitionLikeRepository.save(petitionLike);
        }else{
            if(petitionLike1.getIsLike() == isLike)
                petitionLikeRepository.delete(petitionLike1);
            else{
                petitionLike1.setIsLike(isLike);
                petitionLikeRepository.save(petitionLike1);
            }
        }
    }

    public List<CommentDTO> getComments(String email, Long petitionId){
        Petition petition = getPetitionById(petitionId);
        User user = getUserByEmail(email);

        List<PetitionComment> comments = petitionCommentRepository.findByPetition(petition);
        return commentFacade.commentsToCommentsDTOs(comments,user);
    }
    private PetitionLike getPetitionLikeByUserAndPetition(User user, Petition petition) {
        return petitionLikeRepository.getPetitionLikeByUserAndPetition(user, petition).orElse(null);
    }


    public Petition getPetitionById(Long petitionId) {
        return petitionRepository.findById(petitionId).orElseThrow(() -> new RuntimeException("Петиция не найдена"));
    }

    public User getUserById(Long userId) {
        return userRepository.findUserById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public List<PetitionContent> getPetitionsToSpeech() {
        return petitionFacade.mapPetitionsToPetitionContents(petitionRepository.findAll());
    }

    public PetitionComment createComment(String email, Long petitionId, String comment) {
        Petition petition = getPetitionById(petitionId);
        User user = getUserByEmail(email);
        PetitionComment petitionComment = new PetitionComment();
        petitionComment.setPetition(petition);
        petitionComment.setComment(comment);
        petitionComment.setUser(user);
        return petitionCommentRepository.save(petitionComment);
    }

    public void followPetition(String email, Long petitionId) {
        User user = getUserByEmail(email);
        Petition petition = getPetitionById(petitionId);
        if(petition.getFollowers().contains(user)){
            petition.removeFollower(user);
        }else{
            petition.addFollower(user);
        }
        petitionRepository.save(petition);
    }

    public void likeComment(String email, Long commentId, Boolean isLike) {
        User user = getUserByEmail(email);
        PetitionComment petitionComment = getPetitionCommentById(commentId);
        PetitionCommentLike commentLike = getPetitionCommentLike(user, petitionComment);

        if (commentLike == null) {
            PetitionCommentLike petitionCommentLike = new PetitionCommentLike();
            petitionCommentLike.setComment(petitionComment);
            petitionCommentLike.setUser(user);
            petitionCommentLike.setIsLike(isLike);
            petitionCommentLikeRepository.save(petitionCommentLike);
        }else{
            if(commentLike.getIsLike() == isLike)
                petitionCommentLikeRepository.delete(commentLike);
            else{
                commentLike.setIsLike(isLike);
                petitionCommentLikeRepository.save(commentLike);
            }
        }
    }

    private PetitionCommentLike getPetitionCommentLike(User user, PetitionComment petitionComment) {
        return petitionCommentLikeRepository.findByUserAndComment(user,petitionComment).orElse(null);
    }

    private PetitionComment getPetitionCommentById(Long commentId) {
        return petitionCommentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Комментарий не найден"));
    }
}
