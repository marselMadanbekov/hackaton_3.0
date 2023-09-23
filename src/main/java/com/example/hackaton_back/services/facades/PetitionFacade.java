package com.example.hackaton_back.services.facades;

import com.example.hackaton_back.dao.PetitionContent;
import com.example.hackaton_back.dao.PetitionDTO;
import com.example.hackaton_back.entities.petitions.Petition;
import com.example.hackaton_back.entities.User;
import com.example.hackaton_back.repositories.petitions.PetitionLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PetitionFacade {
    private final PetitionLikeRepository petitionLikeRepository;

    @Autowired
    public PetitionFacade(PetitionLikeRepository petitionLikeRepository) {
        this.petitionLikeRepository = petitionLikeRepository;
    }

    public PetitionContent petitionToPetitionContent(Petition petition){
        PetitionContent petitionContent = new PetitionContent();
        petitionContent.setId(petition.getId());
        petitionContent.setRuDescription(petition.getRuDescription());
        petitionContent.setRuTitle(petition.getRuTitle());

        return petitionContent;
    }

    public PetitionDTO petitionToPetitionDTO(Petition petition, User user){
        PetitionDTO petitionDTO = new PetitionDTO();
        petitionDTO.setId(petition.getId());
        petitionDTO.setCreator(petition.getCreator());
        petitionDTO.setKgTitle(petition.getKgTitle());
        petitionDTO.setRuTitle(petition.getRuTitle());
        petitionDTO.setKgDescription(petition.getKgDescription());
        petitionDTO.setRuDescription(petition.getRuDescription());
        petitionDTO.setPhoto(petition.getPhoto());
        petitionDTO.setLikeCount(petitionLikeRepository.countByPetitionAndIsLike(petition,true).orElse(0));
        petitionDTO.setDisLikeCount(petitionLikeRepository.countByPetitionAndIsLike(petition,false).orElse(0));
        petitionLikeRepository.getPetitionLikeByUserAndPetition(user, petition).ifPresent(petitionLike -> petitionDTO.setIsLike(petitionLike.getIsLike()));

        return petitionDTO;
    }

    public List<PetitionContent> mapPetitionsToPetitionContents(List<Petition> petitions) {
        return petitions.stream()
                .map(this::petitionToPetitionContent)
                .collect(Collectors.toList());
    }
    public List<PetitionDTO> mapPetitionsToPetitionDTOS(List<Petition> petitions, User user) {
        List<PetitionDTO> petitionDTOS = new ArrayList<>();
        for(Petition petition : petitions){
            petitionDTOS.add(petitionToPetitionDTO(petition, user));
        }
        return petitionDTOS;
    }

}
