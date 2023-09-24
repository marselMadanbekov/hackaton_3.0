package com.example.hackaton_back.controllers;

import com.example.hackaton_back.entities.laws.Chapter;
import com.example.hackaton_back.entities.laws.Law;
import com.example.hackaton_back.entities.laws.LawCategory;
import com.example.hackaton_back.entities.laws.Section;
import com.example.hackaton_back.services.LawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/law")
public class LawController {

    private final LawService lawService;

    @Autowired
    public LawController(LawService lawService) {
        this.lawService = lawService;
    }

    @GetMapping("/laws")
    public ResponseEntity<Map<String, List<Law>>> getLaws(){
        Map<String, List<Law>> data = new HashMap<>();
        List<Law> laws = lawService.getAll();
        data.put("data", laws);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("/categories")
    public ResponseEntity<Map<String, List<LawCategory>>> getCategories(){
        Map<String, List<LawCategory>> data = new HashMap<>();
        List<LawCategory> categories = lawService.getAllCategories();
        data.put("data", categories);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("/sections/{categoryId}")
    public ResponseEntity<Map<String, List<Section>>> getSections(@PathVariable Long categoryId){
        Map<String, List<Section>> data = new HashMap<>();
        List<Section> sections = lawService.getSectionsByCategoryId(categoryId);
        data.put("data", sections);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("/chapters/{sectionId}")
    public ResponseEntity<Map<String, List<Chapter>>> getChapters(@PathVariable Long sectionId){
        Map<String, List<Chapter>> data = new HashMap<>();
        List<Chapter> chapters = lawService.getChaptersBySectionId(sectionId);
        data.put("data", chapters);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("/laws/{chapterId}")
    public ResponseEntity<Map<String, List<Law>>> getLaws(@PathVariable Long chapterId){
        Map<String, List<Law>> data = new HashMap<>();
        List<Law> laws = lawService.getLawsByChapterId(chapterId);
        data.put("data", laws);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/create-law")
    public ResponseEntity<Object> createLaw(@RequestParam String title,
                                            @RequestParam String law,
                                            @RequestParam Long chapterId) {
        Map<String, String> response = new HashMap<>();
        try {
            Law law1 = lawService.createLaw(title,law,chapterId);
            return ResponseEntity.ok(law1);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-category")
    public ResponseEntity<Object> createLawCategory(@RequestParam String name) {
        Map<String, String> response = new HashMap<>();
        try {
            LawCategory category = lawService.createLawCategory(name);
            return ResponseEntity.ok(category);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-chapter")
    public ResponseEntity<Object> createLawChapter(@RequestParam String name,
                                                   @RequestParam Long sectionId) {
        Map<String, String> response = new HashMap<>();
        try {
            Chapter chapter = lawService.createLawChapter(name, sectionId);

            return ResponseEntity.ok(chapter);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-section")
    public ResponseEntity<Object> createLawSection(@RequestParam String name,
                                                   @RequestParam Long categoryId) {
        Map<String, String> response = new HashMap<>();
        try {
            Section section = lawService.createLawSection(name, categoryId);
            return ResponseEntity.ok(section);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
