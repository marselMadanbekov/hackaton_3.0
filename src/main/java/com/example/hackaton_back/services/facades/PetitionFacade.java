package com.example.hackaton_back.services.facades;

import com.example.hackaton_back.dao.PetitionContent;
import com.example.hackaton_back.dao.PetitionDTO;
import com.example.hackaton_back.entities.petitions.Petition;
import com.example.hackaton_back.entities.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PetitionFacade {


    public PetitionContent petitionToPetitionContent(Petition petition){
        PetitionContent petitionContent = new PetitionContent();
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
        petitionDTO.setIsFollow(petition.getFollowers().contains(user));
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
