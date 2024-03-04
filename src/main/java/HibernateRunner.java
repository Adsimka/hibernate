import domain.BirthDate;
import domain.Role;
import entity.Company;
import entity.Payment;
import entity.PersonalInfo;
import entity.User;
import jakarta.persistence.LockModeType;
import util.HibernateUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
        nonTransactionalDataAccess();
    }

    private static void nonTransactionalDataAccess() {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()) {

            Payment payment1 = Payment.builder()
                    .amount(2000L)
                    .receivers(session.get(User.class, 1L))
                    .build();

            session.save(payment1);

            var payment = session.find(Payment.class, 1L);
            payment.setAmount(payment.getAmount() + 123L);
        }
    }
    private static void readOnlyMode() {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("SET TRANSACTION READ ONLY;").executeUpdate();

            var payment = session.find(Payment.class, 1L);
            payment.setAmount(payment.getAmount() + 123L);

            session.getTransaction().commit();
        }
    }

    private static void checkFirstWinCommit() {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession();
            var session1 = sessionFactory.openSession()) {

            session.beginTransaction();
            session1.beginTransaction();

            Map<String, Object> properties = Map.of("javax.persistence.lock.timeout", 5000);
            Payment payment = session.find(Payment.class, 1L, LockModeType.READ, properties);
            payment.setAmount(payment.getAmount() + 30L);

            Payment payment1 = session1.find(Payment.class, 1L);
            payment1.setAmount(payment1.getAmount() + 20L);

            Payment payment2 = session.find(Payment.class, 1L);
            System.out.println(payment2);
            System.out.println("---------------------------------------------");

            session1.getTransaction().commit();
            session.getTransaction().commit();
        }
    }

    private static void getPaymentList() {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var user = session.find(User.class, 1L);
            user.getPaymentList().forEach(System.out::println);


            session.getTransaction().commit();
        }
    }

    private static void insertPayment() {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("sah02@mail.ru")
                    .build();

            Payment payment = Payment.builder()
                    .amount(10000L)
                    .receivers(user)
                    .build();

            List<Payment> paymentList = new ArrayList<>();
            paymentList.add(payment);

            user.setPaymentList(paymentList);

            session.save(user);
            session.save(payment);


            session.getTransaction().commit();
        }
    }

    private static void insertUserWithPayments() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 1L);

            List<Payment> paymentList = new ArrayList<>();

            var payment1 = Payment.builder()
                    .amount(1250L)
                    .receivers(user)
                    .build();

            var payment2 = Payment.builder()
                    .amount(1250L)
                    .receivers(user)
                    .build();

            paymentList.add(payment1);
            paymentList.add(payment2);

            user.setPaymentList(paymentList);

            session.saveOrUpdate(payment1);
            session.saveOrUpdate(payment2);

            user.setPaymentList(paymentList);

            session.saveOrUpdate(user);

            session.getTransaction().commit();
        }
    }

    private static void insertUser() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

//            Company company = Company.builder()
//                    .name("Amazon")
//                    .build();
//
//            User user = User.builder()
//                    .username("sahalysyk02@mail.ru")
//                    .company(company)
//                    .personalInfo(PersonalInfo.builder()
//                            .firstName("Arsen")
//                            .lastName("Minnegulov")
//                            .birthday(new BirthDate(LocalDate.of(2002, Month.MARCH, 24)))
//                            .build())
//                    .role(Role.ADMIN)
//                    .build();

            Company company1 = Company.builder()
                    .name("Yandex")
                    .build();

            User user1 = User.builder()
                    .username("sahalysyk01@mail.ru")
                    .company(company1)
                    .personalInfo(PersonalInfo.builder()
                            .firstName("Liza")
                            .lastName("Lapuka")
                            .birthday(new BirthDate(LocalDate.of(2002, Month.MARCH, 24)))
                            .build())
                    .role(Role.ADMIN)
                    .build();


            session.save(company1);
            session.save(user1);

            session.getTransaction().commit();
        }
    }
}
