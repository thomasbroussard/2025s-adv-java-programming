package fr.epita.quiz.services;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.resources.MCQChoiceDTO;
import fr.epita.quiz.resources.QuestionDTO;

import java.util.ArrayList;
import java.util.List;

public class RestChannelDataService {

    GenericDataService genericDataService;
    public RestChannelDataService(GenericDataService genericDataService){
        this.genericDataService = genericDataService;
    }

    public QuestionDTO getQuestion(Integer id){
        Question question = this.genericDataService.getQuestion(id);
        List<MCQChoice> choices = this.genericDataService.getChoices(question);

        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        List<MCQChoiceDTO> choicesDTO = new ArrayList<>();
        for (MCQChoice choice : choices){
            MCQChoiceDTO choiceDTO = new MCQChoiceDTO();
            choiceDTO.setId(choice.getId());
            choiceDTO.setChoiceTitle(choice.getTitle());
            choiceDTO.setValid(choice.isValid());
            choicesDTO.add(choiceDTO);
        }
        dto.setChoices(choicesDTO);
        return dto;

    }
    public List<QuestionDTO> getQuestions(){
        Question question = this.genericDataService.getQuestion();
        List<MCQChoice> choices = this.genericDataService.getChoices(question);

        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        List<MCQChoiceDTO> choicesDTO = new ArrayList<>();
        for (MCQChoice choice : choices){
            MCQChoiceDTO choiceDTO = new MCQChoiceDTO();
            choiceDTO.setId(choice.getId());
            choiceDTO.setChoiceTitle(choice.getTitle());
            choiceDTO.setValid(choice.isValid());
            choicesDTO.add(choiceDTO);
        }
        dto.setChoices(choicesDTO);
        return dto;

    }

}
