package fr.epita.quiz.services.data;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.api.MCQChoiceRepository;
import fr.epita.quiz.services.api.QuestionRepository;
import fr.epita.quiz.services.api.exceptions.PersistenceException;
import jakarta.transaction.Transactional;

import java.util.List;

public class GenericDataService {

    private final QuestionRepository questionRepository;
    private final MCQChoiceRepository mcqChoiceRepo;


    //I need to save Question and Choices

    public GenericDataService(QuestionRepository questionRepository,
                              MCQChoiceRepository mcqChoiceRepository) {
        this.questionRepository = questionRepository;
        this.mcqChoiceRepo = mcqChoiceRepository;
    }

    @Transactional
    public void createQuestion(Question question, List<MCQChoice> mcqChoices) throws PersistenceException {
       this.questionRepository.create(question);
       for (MCQChoice mcqChoice : mcqChoices) {
           mcqChoice.setQuestion(question);
           this.mcqChoiceRepo.create(mcqChoice);
       }
    }



}
