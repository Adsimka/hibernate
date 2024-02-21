package dao;

import entity.Payment;
import entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    public List<User> findAll(Session session) {
        return session.createQuery("select u from User u", User.class).list();
    }

    public List<User> findAllByFirstName(Session session, String firstname) {
        return session.createQuery("select  u from User u where u.personalInfo.firstName = :firstname", User.class)
                .setParameter("firstname", firstname)
                .list();
    }

    public List<User> findLimitedUsersOrderedByLastName(Session session, int limit) {
        return session.createQuery("select u from User u order by u.personalInfo.lastName", User.class)
                .setMaxResults(limit)
                .list();
    }

    public List<User> findByAllCompanyName(Session session, String companyName) {
        return session.createQuery("select u from Company c join c.users u where c.name = :companyName", User.class)
                .setParameter("companyName", companyName)
                .list();
    }

    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        return session.createQuery("select p from Payment p " +
                        "join p.receivers u join u.company c " +
                        "where c.name = :companyName " +
                        "order by u.personalInfo.firstName, p.amount", Payment.class)
                .setParameter("companyName", companyName)
                .list();
    }

    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstName, String lastName) {
        return session.createQuery("select avg(p.amount) from Payment p " +
                        "join p.receivers u " +
                        "where u.personalInfo.firstName = :firstName " +
                        "and u.personalInfo.lastName = :lastName", Double.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .uniqueResult();
    }

    public List<Object[]> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        return session.createQuery("select c.name, avg(p.amount) from Company c " +
                        "join c.users u " +
                        "join u.paymentList p " +
                        "group by p.amount " +
                        "order by c.name", Object[].class)
                .list();
    }

    public List<Object[]> isItPossible(Session session) {
        return session.createQuery("select c.users, avg(p.amount) from Company c " +
                        "join c.users u " +
                        "join u.paymentList p " +
                        "group by c.users " +
                        "having avg(p.amount) > (select avg(p.amount) " +
                        "from Payment p)", Object[].class)
                .list();
    }
    public UserDao getInstance() {
        return INSTANCE;
    }
}
