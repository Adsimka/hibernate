package dao;

import dto.CompanyDto;
import entity.Company;
import entity.Payment;
import entity.User;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.hibernate.query.criteria.JpaJoin;

import java.util.ArrayList;
import java.util.List;

public class UserDaoCriteriaAPI {

    private static final UserDaoCriteriaAPI INSTANCE = new UserDaoCriteriaAPI();

    public List<User> findAll(Session session) {
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(User.class);
        var user = criteria.from(User.class);
        criteria.select(user);

        return session.createQuery(criteria).list();
    }

    public List<User> findAllByFirstName(Session session, String firstname) {
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(User.class);
        var user = criteria.from(User.class);
        criteria.select(user).where(criteriaBuilder.equal(user.get("personalInfo").get("firstname"), firstname));

        return session.createQuery(criteria)
                .list();
    }

    public List<User> findLimitedUsersOrderedByLastName(Session session, int limit) {
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(User.class);
        var user = criteria.from(User.class);

        criteria.select(user).orderBy(criteriaBuilder.asc(user.get("personalInfo").get("lastname")));

        return session.createQuery(criteria)
                .setMaxResults(limit)
                .list();
    }

    public List<User> findByAllCompanyName(Session session, String companyName) {
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(User.class);
        var company = criteria.from(Company.class);

        JpaJoin<Company, User> users = company.join("users");
        criteria.select(users).where(criteriaBuilder.equal(company.get("name"), companyName));

        return session.createQuery(criteria)
                .list();
    }

    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(Payment.class);

        var payment = criteria.from(Payment.class);
        var user = payment.join("receivers");
        var company = user.join("company");

        criteria.select(payment)
                .where(criteriaBuilder.equal(company.get("name"), companyName))
                .orderBy(
                        criteriaBuilder.asc(user.get("personalInfo").get("firstName")),
                        criteriaBuilder.asc(payment.get("amount"))
                );

        return session.createQuery(criteria)
                .list();
    }

    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstName, String lastName) {
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Double.class);

        var payment = criteriaQuery.from(Payment.class);
        var user = payment.join("receivers");

        List<Predicate> predicates = new ArrayList<>();
        if (firstName != null) {
            predicates.add(criteriaBuilder.equal(user.get("personalInfo").get("firstName"), firstName));
        }
        if (lastName != null) {
            predicates.add(criteriaBuilder.equal(user.get("personalInfo").get("lastName"), lastName));
        }
        criteriaQuery.select(criteriaBuilder.avg(payment.get("amount")))
                .where(
                        predicates.toArray(Predicate[]::new)
                );

        return session.createQuery(criteriaQuery)
                .uniqueResult();
    }

    public List<CompanyDto> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(CompanyDto.class);

        var company = criteria.from(Company.class);
        var users = company.join("users");
        var paymentList = users.get("paymentList");

        criteria.multiselect(cb.construct(CompanyDto.class,
                        company.get("name"),
                        cb.avg(paymentList.get("amount"))))
                .groupBy(paymentList.get("amount"))
                .orderBy(cb.asc(company.get("name")));

        return session.createQuery(criteria).list();
    }

    public List<Object[]> isItPossible(Session session) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Object[].class);

        var company = criteria.from(Company.class);
        var users = criteria.from(User.class);
        var paymentList = users.join("paymentList");

        var subquery = criteria.subquery(Double.class);
        var paymentSubquery = criteria.from(Payment.class);

        criteria.multiselect(
                        users,
                        cb.avg(paymentList.get("amount")))
                .groupBy(users.get("id"))
                .having(cb.gt(
                        cb.avg(paymentList.get("amount")),
                        subquery.select(cb.avg(paymentSubquery.get("amount")))
                ))
                .orderBy(cb.asc(users.get("personalInfo").get("firstName")));

        return session.createQuery(criteria)
                .list();
    }

    public UserDaoCriteriaAPI getInstance() {
        return INSTANCE;
    }
}
