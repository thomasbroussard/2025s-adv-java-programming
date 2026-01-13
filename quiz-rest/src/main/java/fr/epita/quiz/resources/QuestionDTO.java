package fr.epita.quiz.resources;



import java.util.List;

public class QuestionDTO {
    Integer id;
    String title;
    List<MCQChoiceDTO> choices;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MCQChoiceDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<MCQChoiceDTO> choices) {
        this.choices = choices;
    }
}
