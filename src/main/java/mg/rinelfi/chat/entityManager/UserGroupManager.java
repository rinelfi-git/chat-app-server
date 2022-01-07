package mg.rinelfi.chat.entityManager;

import mg.rinelfi.chat.entity.relation.UserGroup;
import mg.rinelfi.chat.entity.emmbed.UserGroupKey;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserGroupManager {
    private final MySessionFactory factory;
    
    public UserGroupManager(MySessionFactory instance) {
        this.factory = instance;
    }
    
    public UserGroupKey create(UserGroup link) {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserGroupKey id = (UserGroupKey) session.save(link);
        if (transaction != null) {
            if (id != null)
                transaction.commit();
            else
                transaction.rollback();
        }
        session.close();
        return id;
    }
}
