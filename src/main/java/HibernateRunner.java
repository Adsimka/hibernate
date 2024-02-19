import entity.Company;

import java.sql.SQLException;

import static util.HibernateUtil.buildSessionFactory;


public class HibernateRunner {

    public static void main(String[] args) throws SQLException {


        try (var sessionFactory = buildSessionFactory();
        var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var company = session.get(Company.class, 1);

//            User user = User.builder()
//                    .username("ildan_galay@mail.ru")
//                    .personalInfo(PersonalInfo
//                            .builder()
//                            .firstName("Ildan")
//                            .lastName("Gala")
//                            .birthday(new BirthDate(LocalDate.of(2002, Month.MARCH, 24)))
//                            .build())
//                    .company(company)
//                    .role(Role.USER)
//                    .build();
//
//            session.saveOrUpdate(user);

            session.getTransaction().commit();
        }
    }
}
