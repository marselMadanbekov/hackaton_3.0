package com.example.hackaton_back.services;

import com.example.hackaton_back.configuration.ChatConfig;
import com.example.hackaton_back.entities.Analyze;
import com.example.hackaton_back.entities.petitions.Petition;
import com.example.hackaton_back.repositories.AnalyzeRepository;
import com.example.hackaton_back.repositories.petitions.PetitionRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GPTService {
    private final PetitionRepository petitionRepository;
    private final AnalyzeRepository analyzeRepository;

    @Autowired
    public GPTService(PetitionRepository petitionRepository, AnalyzeRepository analyzeRepository) {
        this.petitionRepository = petitionRepository;
        this.analyzeRepository = analyzeRepository;
    }

    public String sendOpenAIRequest(String requestBody) throws JSONException {
        System.out.println(requestBody);
        String apiUrl = "https://api.openai.com/v1/completions";

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("model", "text-davinci-003");
        jsonObject1.put("prompt", requestBody);
        jsonObject1.put("max_tokens", 1000);
        jsonObject1.put("top_p", 0.1);


        // Создание заголовков запроса
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ChatConfig.OPENAI_API_KEY);
        String jsonString = jsonObject1.toString();
        // Создание объекта запроса
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);

        // Создание объекта RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Отправка запроса
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        // Обработка ответа
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();

            // Извлечение текста ответа
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray choicesArray = jsonObject.getAsJsonArray("choices");
            if (choicesArray.size() > 0) {
                JsonObject choiceObject = choicesArray.get(0).getAsJsonObject();
                String answerText = choiceObject.get("text").getAsString();

                return answerText;
            } else {
                return "Ответ не найден";
            }
        } else {
            return "Ошибка при отправке запроса";
        }
    }

    public Analyze analyzePetition(Petition petition) {
        String header = "Проанализируй законопроект предложенный ниже, для страны с населением 7 000 000 человек, ВВП 723 122.2 млн. сом" +
                " и средняя заработная плата составляет 30 000 сом. Я хочу узнать какие сферы может затронуть этот законопроект, и какую оценку ты даш" +
                " от 1 до 10. Формат ответа я жду сначала оценку от 1 до 10 за которым следует // и дальше твой анализ законопроекта. Далее сам законопроект - ";

        String requestText = header + petition.getRuDescription();

        String response;
        try {
            response = sendOpenAIRequest(requestText);
        } catch (JSONException e) {
            throw new RuntimeException("Oшибка запроса " + e.getMessage());
        }

        System.out.println(response);

        String[] responseParts = response.split("//");

        Analyze analyze = new Analyze();
        try {
            for (Character c : responseParts[0].toCharArray()) {
                if (Character.isDigit(c)) {
                    int rating = Character.getNumericValue(c);
                    analyze.setRating(rating);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        analyze.setText(responseParts[1]);

        analyzeRepository.save(analyze);

        petition.setAnalyze(analyze);
        petitionRepository.save(petition);
        return analyze;
    }
}
