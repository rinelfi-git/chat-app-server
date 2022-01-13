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
        long id;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            id = (long) session.save(user);
            if (transaction != null) {
                if (id > 0)
                    transaction.commit();
                else
                    transaction.rollback();
            }
        }
        return id;
    }

    public String getPassword(String username) {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        TypedQuery<String> query = session.createQuery("select user.password from User user where user.username = :username", String.class);
        query.setParameter("username", username);
        List<String> result = query.getResultList();
        String output = result.size() > 0 ? query.getSingleResult() : null;
        session.close();
        return output;
    }

    public User get(String field, Object value) {
        SessionFactory sessionFactory = this.factory.getSession();
        User output;
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<User> query = session.createQuery(String.format("from User user where user.%s = :%s", field, field), User.class);
            query.setParameter(field, value);
            List<User> result = query.getResultList();
            output = result.size() > 0 ? query.getSingleResult() : null;
        }
        return output;
    }

    public void update(User user) {
        SessionFactory sessionFactory = this.factory.getSession();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }
    }

    public List<User> select() {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        TypedQuery<User> query = session.createQuery("select user from User user", User.class);
        List<User> output = query.getResultList();
        session.close();
        return output;
    }
    
    public List<User> select(String name) {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        TypedQuery<User> query = session.createQuery("select u from User u where upper(u.username) like :name or upper(u.firstname) like :name or upper(u.lastname) like :name", User.class);
        query.setParameter("name", "%" + name.toUpperCase() + "%");
        List<User> output = query.getResultList();
        session.close();
        return output;
    }
}
