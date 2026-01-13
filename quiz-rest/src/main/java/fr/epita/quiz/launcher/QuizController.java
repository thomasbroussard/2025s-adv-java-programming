package fr.epita.quiz.launcher;


import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.resources.MCQChoiceDTO;
import fr.epita.quiz.resources.QuestionDTO;
import fr.epita.quiz.rest.resources.QuestionListDTO;
import fr.epita.quiz.services.GenericDataService;
import fr.epita.quiz.services.RestChannelDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class QuizController {

    private final RestChannelDataService dataService;

    public QuizController(RestChannelDataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public QuestionListDTO getQuestionsList(){

    }


    @GetMapping("questions/${id}")
    public QuestionDTO getQuestion(@PathVariable("id") String id){
       return dataService.getQuestion(Integer.parseInt(id));
    }



}
