package fr.epita.quiz.launcher;

import fr.epita.quiz.services.GenericDataService;
import fr.epita.quiz.services.RestChannelDataService;
import fr.epita.quiz.services.api.MCQChoiceRepository;
import fr.epita.quiz.services.api.QuestionRepository;
import fr.epita.quiz.services.api.conf.DBProperties;
import fr.epita.quiz.services.impl.jpa.JPAMCQChoiceRepository;
import fr.epita.quiz.services.impl.jpa.JPAQuestionRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.hibernate.HibernateTransactionManager;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties(DBProperties.class)
@EnableTransactionManagement
public class Config {



    @Bean("services.data.datasource")
    public DataSource getPrimaryDataSource(DBProperties dbProperties) {
        return new DriverManagerDataSource(dbProperties.getUrl(), dbProperties.getUser(),dbProperties.getPwd());
    }

    @Bean("services.data.altdatasource")
    public DataSource getSecondaryDataSource() {
        return new DriverManagerDataSource("jdbc:h2:mem:test2;DB_CLOSE_DELAY=-1", "user", "password");
    }

    @Bean
    @Scope(value = "singleton") //scopes are by default "singleton" (one creation for the whole application)
    public QuestionRepository jpaQuestionRepository(SessionFactory sessionFactory) {
        return new JPAQuestionRepository(sessionFactory);
    }

    @Bean
    @Scope(value = "singleton") //scopes are by default "singleton" (one creation for the whole application)
    public MCQChoiceRepository jpaMCQRepository(SessionFactory sessionFactory) {
        return new JPAMCQChoiceRepository(sessionFactory);
    }



    @Bean("jpa.hibernate.properties")
    public Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");
        return hibernateProperties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(@Qualifier("services.data.datasource") DataSource dataSource,
                                                      @Qualifier("jpa.hibernate.properties") Properties hibernateProperties) {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setHibernateProperties(hibernateProperties);
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan("fr.epita.quiz.datamodel");
        return localSessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory,
                                                          @Qualifier("services.data.datasource") DataSource dataSource){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        transactionManager.setDataSource(dataSource); // Enable DataSourceUtils connection binding
        return transactionManager;
    }


    @Bean
    public GenericDataService getGenericDataService(QuestionRepository questionRepository, MCQChoiceRepository mcqChoiceRepository) {
        return new GenericDataService(questionRepository, mcqChoiceRepository);
    }

    @Bean
    public RestChannelDataService getRestChannelDataService(GenericDataService genericDataService,
            QuestionRepository questionRepository, MCQChoiceRepository choiceRepository) {
        return new RestChannelDataService(genericDataService, questionRepository, choiceRepository);
    }


}
