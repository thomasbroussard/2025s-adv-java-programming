package fr.epita.quiz.tests;


import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = fr.epita.quiz.tests.TestConfig.class)
public class TestQuestionRepository {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    @Qualifier("services.data.datasource")
    DataSource dataSource;


    @Test
    public void testQuestionRepository_create_nominal() throws SQLException {
        //given
        Question question = new Question("Test");

        //when
        questionRepository.create(question);

        //then
        Connection connection = dataSource.getConnection();
        ResultSet resultSet = connection.prepareStatement("SELECT title FROM question").executeQuery();
        resultSet.next();
        Assertions.assertEquals(question.getTitle(), resultSet.getString("title"));
        connection.close();
    }

    @Test
    public void testQuestionRepository_findByTitle_nominal() throws SQLException {
        //given
        Question question = new Question("Test");
        questionRepository.create(question);

        //when
        questionRepository.findByTitle("Tes");


        //then
        Connection connection = dataSource.getConnection();
        ResultSet resultSet = connection.prepareStatement("SELECT title FROM question").executeQuery();
        resultSet.next();
        Assertions.assertEquals(question.getTitle(), resultSet.getString("title"));
        connection.close();
    }

}
