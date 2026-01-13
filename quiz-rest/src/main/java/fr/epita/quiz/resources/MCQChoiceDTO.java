package fr.epita.quiz.resources;

public class MCQChoiceDTO {

    Integer id;
    String choiceTitle;
    Boolean valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChoiceTitle() {
        return choiceTitle;
    }

    public void setChoiceTitle(String choiceTitle) {
        this.choiceTitle = choiceTitle;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
