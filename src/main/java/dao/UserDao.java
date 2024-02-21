package dao;

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

    public
    public UserDao getInstance() {
        return INSTANCE;
    }
}
