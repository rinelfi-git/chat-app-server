package mg.rinelfi.chat.entityManager;

import mg.rinelfi.chat.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserManager {
    private final MySessionFactory factory;
    
    public UserManager(MySessionFactory instance) {this.factory = instance;}
    
    public void insert(User user) {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(user);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
