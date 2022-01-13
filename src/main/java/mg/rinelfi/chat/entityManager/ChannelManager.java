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
    
    public Channel get(String field, Object value) {
        SessionFactory sessionFactory = this.factory.getSession();
        Channel output;
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Channel> query = session.createQuery(String.format("from Channel chan where chan.%s = :%s", field, field), Channel.class);
            query.setParameter(field, value);
            List<Channel> result = query.getResultList();
            output = result.size() > 0 ? query.getSingleResult() : null;
        }
        return output;
    }
    
    public List<Channel> selectFromUser(long user) {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        TypedQuery<Channel> query = session.createQuery("select c from Channel c left join UserGroup ug on ug.group.id=c.id left join UserChannelUser ucu on ucu.channel.id=c.id where ucu.user.id = :user or ug.user.id = :user", Channel.class);
        query.setParameter("user", user);
        List<Channel> select = query.getResultList();
        session.close();
        return select;
    }
}
