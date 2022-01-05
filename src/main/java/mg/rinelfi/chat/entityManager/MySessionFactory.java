package mg.rinelfi.chat.entityManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MySessionFactory {
    private static MySessionFactory sessionFactory;
    private SessionFactory factory;
    
    static {
        MySessionFactory.sessionFactory = new MySessionFactory();
    }
    
    private MySessionFactory() {
        this.factory = new Configuration().configure().buildSessionFactory();
    }
    
    public static MySessionFactory getInstance() {
        return MySessionFactory.sessionFactory;
    }
    
    public SessionFactory getSession() {
        return this.factory;
    }
}
