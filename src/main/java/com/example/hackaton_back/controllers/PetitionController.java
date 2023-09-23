package com.example.hackaton_back.controllers;

import com.example.hackaton_back.dao.PetitionContent;
import com.example.hackaton_back.dao.PetitionDTO;
import com.example.hackaton_back.payload.request.CreatePetitionRequest;
import com.example.hackaton_back.services.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/petition")
public class PetitionController {

    private final PetitionService petitionService;

    @Autowired
    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }


    @PostMapping("/create")
    public ResponseEntity<Object> createPetition(@RequestParam String ruTitle,
                                                 @RequestParam String kgTitle,
                                                 @RequestParam String ruDescription,
                                                 @RequestParam String kgDescription,
                                                 @RequestParam String email,
                                                 @RequestParam MultipartFile photo){
        Map<String,String> response = new HashMap<>();
        try{
            petitionService.createPetition(new CreatePetitionRequest(email,ruTitle,kgTitle,ruDescription,kgDescription,photo));
            return ResponseEntity.ok(null);
        }catch (RuntimeException e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("")
    public ResponseEntity<Object> getPetitions(@RequestBody String email){
        List<PetitionDTO> petitions = petitionService.getAllPetitions(email);
        return new ResponseEntity<>(petitions, HttpStatus.OK);
    }

    @GetMapping("/to-speech")
    public ResponseEntity<Map<String,List<PetitionContent>>> getPetitionsToSpeech(){
        Map<String,List<PetitionContent>> data = new HashMap<>();
        List<PetitionContent> petitions = petitionService.getPetitionsToSpeech();
        data.put("data", petitions);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<Object> likePetition(@RequestParam String email,
                                               @RequestParam Long petitionId,
                                               @RequestParam Boolean isLike){
        Map<String,String> response = new HashMap<>();
        try{
            petitionService.likePetition(email,petitionId,isLike);
            return ResponseEntity.ok(null);
        }catch (RuntimeException e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
