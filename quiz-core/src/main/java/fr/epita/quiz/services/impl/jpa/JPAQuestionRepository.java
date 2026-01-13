package fr.epita.quiz.services.impl.jpa;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.api.QuestionRepository;
import fr.epita.quiz.services.api.exceptions.PersistenceException;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@Transactional
public class JPAQuestionRepository
        extends JPAGenericRepository<Question>
        implements QuestionRepository {


    public JPAQuestionRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Question> findByTitle(String title) throws PersistenceException {
        Query<Question> query = this.sessionFactory
                .getCurrentSession()
                .createQuery("from Question where title like :param", Question.class);
        query.setParameter("param", title+"%");
        return query.getResultList();
    }


    @Override
    public List<Question> getAllQuestionsWithLimit(int maxRow) throws PersistenceException {
        Query<Question> query = this.sessionFactory
                .getCurrentSession()
                .createQuery("from Question ", Question.class)
                .setMaxResults(maxRow);
        return query.getResultList();
    }
}
