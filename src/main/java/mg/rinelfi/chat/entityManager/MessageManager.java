/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.rinelfi.chat.entityManager;

import java.util.List;
import javax.persistence.TypedQuery;
import mg.rinelfi.chat.entity.Channel;
import mg.rinelfi.chat.entity.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author rinelfi
 */
public class MessageManager {

    private final MySessionFactory factory;

    public MessageManager(MySessionFactory instance) {
        this.factory = instance;
    }

    public long create(Message message) {
        SessionFactory sessionFactory = this.factory.getSession();
        long id;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            id = (long) session.save(message);
            if (transaction != null) {
                if (id > 0)
                    transaction.commit();
                else
                    transaction.rollback();
            }
        }
        return id;
    }

    public List<Message> select(Channel channel) {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        TypedQuery<Message> query = session.createQuery("select m from Message m where m.channel=:channel order by m.date", Message.class);
        query.setParameter("channel", channel);
        List<Message> output = query.getResultList();
        session.close();
        return output;
    }
}
