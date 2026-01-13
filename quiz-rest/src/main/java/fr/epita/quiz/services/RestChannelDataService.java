package fr.epita.quiz.services;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.rest.resources.MCQChoiceDTO;
import fr.epita.quiz.rest.resources.QuestionDTO;
import fr.epita.quiz.rest.resources.QuestionListDTO;
import fr.epita.quiz.services.api.MCQChoiceRepository;
import fr.epita.quiz.services.api.QuestionRepository;
import fr.epita.quiz.services.api.exceptions.PersistenceException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

public class RestChannelDataService {

    private final QuestionRepository questionRepository;
    private final MCQChoiceRepository mcqChoiceRepository;
    GenericDataService genericDataService;

    public RestChannelDataService(GenericDataService genericDataService,
                                  QuestionRepository repository,
                                MCQChoiceRepository mcqChoiceRepository){
        this.genericDataService = genericDataService;
        this.questionRepository = repository;
        this.mcqChoiceRepository = mcqChoiceRepository;
    }

    public QuestionDTO getQuestion(Integer id) throws PersistenceException {
        Question question = this.genericDataService.getQuestion(id);
        return extractDTO(question);
    }

    public QuestionListDTO getQuestions() throws PersistenceException {
        List<Question> questions = this.questionRepository.getAllQuestionsWithLimit(50);
        QuestionListDTO questionListDTO = new QuestionListDTO();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            questionDTOList.add(extractDTO(question));
        }
        questionListDTO.setQuestionDTOList(questionDTOList);
        return questionListDTO;
    }

    private QuestionDTO extractDTO(Question question) {
        List<MCQChoice> choices = this.genericDataService.getChoices(question);

        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        List<MCQChoiceDTO> choicesDTO = new ArrayList<>();
        for (MCQChoice choice : choices) {
            MCQChoiceDTO choiceDTO = new MCQChoiceDTO();
            choiceDTO.setId(choice.getId());
            choiceDTO.setChoiceTitle(choice.getTitle());
            choiceDTO.setValid(choice.isValid());
            choicesDTO.add(choiceDTO);
        }
        return dto;
    }

    @Transactional
    public Question createQuestion(QuestionDTO questionDTO) throws PersistenceException {
        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        this.questionRepository.create(question);
        List<MCQChoiceDTO> choices = questionDTO.getChoices();
        if (choices != null) {
            for (MCQChoiceDTO mcqChoiceDTO : choices){
                MCQChoice mcqChoice = new MCQChoice();
                mcqChoice.setTitle(mcqChoiceDTO.getChoiceTitle());
                mcqChoice.setValid(mcqChoiceDTO.getValid());
                this.mcqChoiceRepository.create(mcqChoice);
                mcqChoiceDTO.setId(mcqChoice.getId());
            }
        }
        questionDTO.setId(question.getId());
        return question;
    }
}
