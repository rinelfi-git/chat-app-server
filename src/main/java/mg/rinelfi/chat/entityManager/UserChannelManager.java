/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.rinelfi.chat.entityManager;

import mg.rinelfi.chat.entity.relation.UserChannelUser;
import mg.rinelfi.chat.entity.emmbed.UserChannelUserKey;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author rinelfi
 */
public class UserChannelManager {
    private final MySessionFactory factory;

    public UserChannelManager(MySessionFactory instance) {
        this.factory = instance;
    }
    
    public UserChannelUserKey create(UserChannelUser link) {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserChannelUserKey id = (UserChannelUserKey) session.save(link);
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
