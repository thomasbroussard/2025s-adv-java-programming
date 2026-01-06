package fr.epita.quiz.services;

import fr.epita.quiz.datamodel.Question;

import java.sql.SQLException;
import java.util.List;

public interface QuestionRepository {


    void create(Question question) throws SQLException;

    List<Question> findByTitle(String title) throws SQLException;

}
