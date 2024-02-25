import entity.User;
import jakarta.persistence.FlushModeType;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

public class HibernateQLTest {

    private static final String USERNAME = "aha@mail.ru";

    @Test
    void CRUDQuery() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("update User u set u.role = 'ADMIN'");

            session.getTransaction().commit();
        }
    }

    @Test
    void useSQLQuery() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var query = session.createNativeQuery("select u from User u join u.company c where u.username = :username and c.name = :companyName", User.class)
                            .setParameter("username", USERNAME)
                            .setParameter("companyName", "Yandex")
                            .list();

            session.getTransaction().commit();
        }
    }

    @Test
    void checkSetHint() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var query = session.createNamedQuery("findUserByName", User.class)
                    .setParameter("username", USERNAME)
                    .setParameter("companyName", "Yandex")
                    .setFlushMode(FlushModeType.COMMIT)
                    .list();


            session.getTransaction().commit();
        }
    }

    @Test
    void useNamedQuery() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var query = session.createNamedQuery("findUserByName", User.class)
                    .setParameter("username", USERNAME)
                    .setParameter("companyName", "Yandex")
                    .list();


            session.getTransaction().commit();
        }
    }

    @Test
    void selectUserByUsernameJoinCompany() {

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var query = session.createQuery("select u from User u join u.company c where u.username = :username and c.name = :companyName", User.class)
                    .setParameter("username", USERNAME)
                    .setParameter("companyName", "Yandex")
                    .list();


            session.getTransaction().commit();
        }
    }

    @Test
    void selectUserByUsername() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var query = session.createQuery("select u from User u where u.username = :username", User.class)
                            .setParameter("username", "aha@mail.ru")
                            .list();

            System.out.println(query);

            session.getTransaction().commit();
        }
    }

    @Test
    void selectUserForEntity() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("select u from User u", User.class);

            session.beginTransaction();
        }
    }

    @Test
    void selectUser() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("select u from User u");

            session.getTransaction().commit();
        }
    }
}
