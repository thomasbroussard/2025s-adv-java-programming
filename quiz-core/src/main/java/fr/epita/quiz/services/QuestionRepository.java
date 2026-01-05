package fr.epita.quiz.services;

import fr.epita.quiz.datamodel.Question;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {

    private static final Logger LOGGER = LogManager.getLogger(QuestionRepository.class);

    private final DataSource ds;

    public QuestionRepository(DataSource ds) throws SQLException {
        LOGGER.info("Initializing Question database backend");
        this.ds = ds;
        Connection connection = ds.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("""
        CREATE TABLE IF NOT EXISTS QUESTION( ID INTEGER auto_increment, title VARCHAR(500))
        """);
        preparedStatement.execute();
        connection.close();
        LOGGER.info("Finished Creating Question database backend");
    }

    public void create(Question question) throws SQLException {
        LOGGER.debug("creating question {}", question.getTitle());
        Connection connection = ds.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO QUESTION (title) VALUES (?)");
        preparedStatement.setString(1, question.getTitle());
        preparedStatement.execute();
        connection.close();
        LOGGER.debug("question created");
    }

    public List<Question> findByTitle(String title) throws SQLException {
        LOGGER.debug("findByTitle {}", title);
        Connection connection = ds.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM QUESTION WHERE title LIKE ?");
        preparedStatement.setString(1, title+"%");

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Question> questions = new ArrayList<>();
        while (resultSet.next()) {
            Question question = new Question(resultSet.getString("title"));
            question.setId(resultSet.getInt("id"));
            questions.add(question);

        }
        LOGGER.debug("found {}", questions.size());
        return  questions;
    }


}
