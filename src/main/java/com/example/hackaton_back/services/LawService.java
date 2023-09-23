package com.example.hackaton_back.services;

import com.example.hackaton_back.entities.laws.Chapter;
import com.example.hackaton_back.entities.laws.Law;
import com.example.hackaton_back.entities.laws.LawCategory;
import com.example.hackaton_back.entities.laws.Section;
import com.example.hackaton_back.repositories.laws.ChapterRepository;
import com.example.hackaton_back.repositories.laws.LawCategoryRepository;
import com.example.hackaton_back.repositories.laws.LawRepository;
import com.example.hackaton_back.repositories.laws.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LawService {
    private final LawRepository lawRepository;
    private final SectionRepository sectionRepository;
    private final ChapterRepository chapterRepository;
    private final LawCategoryRepository lawCategoryRepository;

    @Autowired
    public LawService(LawRepository lawRepository, SectionRepository sectionRepository, ChapterRepository chapterRepository, LawCategoryRepository lawCategoryRepository) {
        this.lawRepository = lawRepository;
        this.sectionRepository = sectionRepository;
        this.chapterRepository = chapterRepository;
        this.lawCategoryRepository = lawCategoryRepository;
    }

    public Law createLaw(String title,String lawCon, Long chapterId){

        Chapter chapter = getChapterById(chapterId);

        Law law = new Law();

        law.setTitle(title);
        law.setLaw(lawCon);
        law.setChapter(chapter);

        return lawRepository.save(law);
    }

    public LawCategory createLawCategory(String title) {
        LawCategory category = new LawCategory();
        category.setName(title);
        lawCategoryRepository.save(category);
        return category;
    }

    public Chapter createLawChapter(String name, Long sectionId) {
        Section section = getSectionById(sectionId);
        Chapter chapter = new Chapter(name, section);
        chapterRepository.save(chapter);
        return chapter;
    }

    public Section createLawSection(String name, Long categoryId) {
        LawCategory category = getCategoryById(categoryId);
        Section section = new Section(name, category);
        sectionRepository.save(section);
        return section;
    }

    private LawCategory getCategoryById(Long categoryId) {
        return lawCategoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Категория не найдена"));
    }


    private Chapter getChapterById(Long id){
        return chapterRepository.findById(id).orElseThrow(() -> new RuntimeException("Глава не найдена"));
    }
    private Section getSectionById(Long id){
        return sectionRepository.findById(id).orElseThrow(() -> new RuntimeException("Раздел не найден"));
    }

    public List<Law> getAll() {
        return lawRepository.findAll();
    }

    public List<LawCategory> getAllCategories() {
        return lawCategoryRepository.findAll();
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }
}
