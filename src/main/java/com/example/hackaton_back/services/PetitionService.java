package com.example.hackaton_back.services;


import com.example.hackaton_back.dao.PetitionContent;
import com.example.hackaton_back.dao.PetitionDTO;
import com.example.hackaton_back.entities.petitions.Petition;
import com.example.hackaton_back.entities.petitions.PetitionLike;
import com.example.hackaton_back.entities.User;
import com.example.hackaton_back.payload.request.CreatePetitionRequest;
import com.example.hackaton_back.repositories.PetitionLikeRepository;
import com.example.hackaton_back.repositories.PetitionRepository;
import com.example.hackaton_back.repositories.UserRepository;
import com.example.hackaton_back.services.facades.PetitionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PetitionService {
    private final PetitionRepository petitionRepository;
    private final PetitionLikeRepository petitionLikeRepository;
    private final PhotoService photoService;
    private final UserRepository userRepository;
    private final PetitionFacade petitionFacade;


    @Autowired
    public PetitionService(PetitionRepository petitionRepository, PetitionLikeRepository petitionLikeRepository, PhotoService photoService, UserRepository userRepository, PetitionFacade petitionFacade) {
        this.petitionRepository = petitionRepository;
        this.petitionLikeRepository = petitionLikeRepository;
        this.photoService = photoService;
        this.userRepository = userRepository;
        this.petitionFacade = petitionFacade;
    }

    public void createPetition(CreatePetitionRequest createPetitionRequest) {
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

        petitionRepository.save(petition);
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
            else
                petitionLike1.setIsLike(isLike);
        }
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
}
