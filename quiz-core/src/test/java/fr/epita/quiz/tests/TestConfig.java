package fr.epita.quiz.tests;

import fr.epita.quiz.services.QuestionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class TestConfig {


    @Bean("services.data.datasource")
    public DataSource getPrimaryDataSource() {
      return new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
    }

    @Bean("services.data.altdatasource")
    public DataSource getSecondaryDataSource() {
      return new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");
    }

    @Bean
    public QuestionRepository getQuestionRepository(@Qualifier("services.data.datasource") DataSource dataSource) throws SQLException {
        return new QuestionRepository(dataSource);
    }




}
