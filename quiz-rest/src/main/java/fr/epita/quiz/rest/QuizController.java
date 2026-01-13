package fr.epita.quiz.rest;


import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.rest.resources.QuestionDTO;
import fr.epita.quiz.rest.resources.QuestionListDTO;
import fr.epita.quiz.services.RestChannelDataService;
import fr.epita.quiz.services.api.exceptions.PersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class QuizController {

    private static final Logger LOGGER = LogManager.getLogger(QuizController.class);
    private final RestChannelDataService dataService;

    public QuizController(RestChannelDataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public QuestionListDTO getQuestionsList(){
        try {
            return this.dataService.getQuestions();
        }catch (Exception e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }


    @GetMapping("questions/{id}")
    public QuestionDTO getQuestion(@PathVariable("id") String id){
        try {
            return dataService.getQuestion(Integer.parseInt(id));
        } catch (PersistenceException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @PostMapping("questions")
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO){
        try {
            Question question = this.dataService.createQuestion(questionDTO);
            questionDTO.setId(question.getId());
            return ResponseEntity.created(URI.create(String.valueOf(question.getId()))).body(questionDTO);
        } catch (PersistenceException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }


}
