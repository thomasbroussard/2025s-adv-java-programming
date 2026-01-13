package fr.epita.quiz.services.impl.jdbc;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.api.QuestionRepository;
import fr.epita.quiz.services.api.exceptions.PersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCQuestionRepository implements QuestionRepository {

    private static final Logger LOGGER = LogManager.getLogger(JDBCQuestionRepository.class);

    private final DataSource ds;

    public JDBCQuestionRepository(DataSource ds) throws SQLException {
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

    public void create(Question question) throws PersistenceException {
        LOGGER.debug("creating question {}", question.getTitle());

        try(Connection connection = ds.getConnection();) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO QUESTION (title) VALUES (?)");
            preparedStatement.setString(1, question.getTitle());
            preparedStatement.execute();
        }catch(SQLException e){
            LOGGER.error(e);
            PersistenceException persistenceException = new PersistenceException();
            persistenceException.initCause(persistenceException);
            throw persistenceException;
        }
        LOGGER.debug("question created");
    }

    @Override
    public void update(Question instance) throws PersistenceException {

    }

    @Override
    public void delete(Question instance) throws PersistenceException {

    }

    @Override
    public Question findById(Class<Question> expectedType, Object id) throws PersistenceException {
        return null;
    }

    public List<Question> findByTitle(String title) throws PersistenceException {
        LOGGER.debug("findByTitle {}", title);

        List<Question> questions = new ArrayList<>();
        try (  Connection connection = ds.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM QUESTION WHERE title LIKE ?");
            preparedStatement.setString(1, title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Question question = new Question(resultSet.getString("title"));
                question.setId(resultSet.getInt("id"));
                questions.add(question);

            }
        }catch (SQLException e){
            LOGGER.error(e);
            PersistenceException persistenceException = new PersistenceException();
            persistenceException.initCause(persistenceException);
            throw persistenceException;
        }
        LOGGER.debug("found {}", questions.size());
        return  questions;
    }

    @Override
    public List<Question> getAllQuestionsWithLimit(int maxRow) throws PersistenceException {
        return List.of();
    }


}
