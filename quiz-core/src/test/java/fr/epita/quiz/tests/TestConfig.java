package fr.epita.quiz.tests;

import fr.epita.quiz.services.JDBCQuestionRepository;
import fr.epita.quiz.services.QuestionRepository;
import fr.epita.quiz.services.conf.DBProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties(DBProperties.class)
public class TestConfig {


    @Bean("services.data.datasource")
    public DataSource getPrimaryDataSource(DBProperties dbProperties) {
      return new DriverManagerDataSource(dbProperties.getUrl(), dbProperties.getUser(),dbProperties.getPwd());
    }

    @Bean("services.data.altdatasource")
    public DataSource getSecondaryDataSource() {
      return new DriverManagerDataSource("jdbc:h2:mem:test2;DB_CLOSE_DELAY=-1", "user", "password");
    }

    @Bean
    public QuestionRepository getQuestionRepository(@Qualifier("services.data.datasource") DataSource dataSource) throws SQLException {
        return new MongoDBQuestionRepository(dataSource);
    }




}
