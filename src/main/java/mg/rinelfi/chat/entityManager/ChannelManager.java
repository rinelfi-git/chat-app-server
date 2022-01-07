package mg.rinelfi.chat.entityManager;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import mg.rinelfi.chat.entity.Channel;
import org.hibernate.Transaction;

public class ChannelManager {

    private final MySessionFactory factory;

    public ChannelManager(MySessionFactory instance) {
        this.factory = instance;
    }

    public List<Channel> select() {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        TypedQuery<Channel> query = session.createQuery("from Channel", Channel.class);
        List<Channel> select = query.getResultList();
        session.close();
        return select;
    }

    public long create(Channel channel) {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        long id = (long) session.save(channel);
        if (transaction != null) {
            if (id > 0)
                transaction.commit();
            else
                transaction.rollback();
        }
        session.close();
        return id;
    }

    public void update(Channel channel) {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(channel);
        if (transaction != null)
            transaction.commit();
        else
            transaction.rollback();
        session.close();
    }
}
