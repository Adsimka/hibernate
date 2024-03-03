import domain.BirthDate;
import domain.Role;
import entity.Company;
import entity.Payment;
import entity.PersonalInfo;
import entity.User;
import util.HibernateUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static util.HibernateUtil.buildSessionFactory;


public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
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
