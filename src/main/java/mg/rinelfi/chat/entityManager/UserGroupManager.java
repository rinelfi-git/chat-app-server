package mg.rinelfi.chat.entityManager;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.TypedQuery;
import mg.rinelfi.chat.entity.Group;
import mg.rinelfi.chat.entity.relation.UserGroup;
import mg.rinelfi.chat.entity.emmbed.UserGroupKey;
import mg.rinelfi.chat.entity.relation.UserChannelUser;
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

    public Set<UserGroup> getMembersFromGroup(Group group) {
        SessionFactory sessionFactory = this.factory.getSession();
        Session session = sessionFactory.openSession();
        TypedQuery<UserGroup> query = session.createQuery("from UserGroup ug where ug.group = :group", UserGroup.class);
        query.setParameter("group", group);
        Set<UserGroup> result = new HashSet<>(query.getResultList());
        session.close();
        return result;
    }
}
