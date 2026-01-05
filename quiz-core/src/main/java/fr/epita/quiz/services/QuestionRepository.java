package fr.epita.quiz.services;

import fr.epita.quiz.datamodel.Question;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuestionRepository {

    private final DataSource ds;

    public QuestionRepository(DataSource ds) throws SQLException {
        this.ds = ds;
        Connection connection = ds.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("""
        CREATE TABLE IF NOT EXISTS QUESTION( ID INTEGER auto_increment, title VARCHAR(500))
        """);
        preparedStatement.execute();
        connection.close();
    }

    public void create(Question question) throws SQLException {
        Connection connection = ds.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO QUESTION (title) VALUES (?)");
        preparedStatement.setString(1, question.getTitle());
        preparedStatement.execute();
        connection.close();
    }
}
