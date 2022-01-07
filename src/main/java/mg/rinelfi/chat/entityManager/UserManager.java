package mg.rinelfi.chat.entityManager;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import mg.rinelfi.chat.entity.User;

public class UserManager {

    private final MySessionFactory factory;

    public UserManager(MySessionFactory instance) {
        this.factory = instance;
    }

    public long create(User user) {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        long id = (long) session.save(user);
        if (transaction != null) {
            if (id > 0)
                transaction.commit();
            else
                transaction.rollback();
        }
        session.close();
        return id;
    }

    public String getPassword(String username) {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        TypedQuery<String> query = session.createQuery("select distinct user.password from User user where user.username = :username", String.class);
        query.setParameter("username", username);
        List<String> result = query.getResultList();
        String output = result.size() > 0 ? query.getSingleResult() : null;
        session.close();
        return output;
    }
}
