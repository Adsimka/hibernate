import entity.Company;
import entity.LocaleInfo;
import entity.Profile;
import entity.User;
import jakarta.persistence.QueryHint;
import lombok.Cleanup;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;
import util.HibernateUtilTest;

import java.util.Map;

class HibernateRunnerTest {

    @Test
    void checkEntityGraph() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            RootGraph<User> userGraph = session.createEntityGraph(User.class);
            userGraph.addAttributeNodes("company", "usersChats");
            SubGraph<Object> usersChats = userGraph.addSubgraph("usersChats");
            usersChats.addAttributeNodes("chat");

//            var userGraph = session.getEntityGraph("withCompanyAndChat");

            Map<String, Object> properties = Map.of(
                    GraphSemantic.LOAD.getJpaHintName(), userGraph
            );

            User user = session.find(User.class, 1L, properties);

            System.out.println(user.getCompany().getName());
            System.out.println(user.getUsersChats().size());

            var users = session.createQuery(
                            "select u from User u " +
                                    "where 1 = 1", User.class)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), userGraph)
                    .list();

            users.forEach(u -> System.out.println(u.getCompany().getName()));
            users.forEach(u -> System.out.println(u.getUsersChats().size()));


            session.getTransaction().commit();
        }
    }

    @Test
    void checkInsertUser() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.enableFetchProfile("withCompanyAndPayments");

            var user = session.get(User.class, 1L);
            System.out.println(user.getCompany());
//            System.out.println(user.getPaymentList());

            session.getTransaction().commit();
        }
    }

//    @Test
//    void buildJoinedTableForSubclassUser() {
//        try (var sessionFactory = HibernateUtilTest.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            var company = Company.builder()
//                    .name("Aaaaas")
//                    .build();
//            session.saveOrUpdate(company);
//
//            var programmer = Programmer.builder()
//                    .username("ssaha@mail.ru")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstName("Marat")
//                            .lastName("Akhujon")
//                            .birthday(new BirthDate(LocalDate.of(2003, 02, 16)))
//                            .build())
//                    .role(ADMIN)
//                    .company(company)
//                    .language(JAVA)
//                    .build();
//            session.save(programmer);
//
//            var manager = Manager.builder()
//                    .username("smaha@mail.ru")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstName("Iluya")
//                            .lastName("Kisha")
//                            .birthday(new BirthDate(LocalDate.of(2007, 07, 21)))
//                            .build())
//                    .role(ADMIN)
//                    .company(company)
//                    .projectName("Create landing paje")
//                    .build();
//            session.save(manager);
//
//            session.flush();
//
//            var programme1 = session.get(Programmer.class, 1L);
//            var manager1 = session.get(Manager.class, 2L);
//
//            System.out.println("------------------------/////-------------------------");
//
//            System.out.println(programme1);
//            System.out.println(manager1);
//
//            System.out.println("------------------------/////-------------------------");
//
//            session.getTransaction().commit();
//        }
//
//    }

//    @Test
//    void buildSingleTableForSubclassUser() {
//        try (var sessionFactory = HibernateUtilTest.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            var company = Company.builder()
//                    .name("Sber")
//                    .build();
//            session.saveOrUpdate(company);
//
//            var programmer = Programmer.builder()
//                    .username("ssaha@mail.ru")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstName("Marat")
//                            .lastName("Akhujon")
//                            .birthday(new BirthDate(LocalDate.of(2003, 02, 16)))
//                            .build())
//                    .role(ADMIN)
//                    .company(company)
//                    .language(JAVA)
//                    .build();
//            session.save(programmer);
//
//            var manager = Manager.builder()
//                    .username("smaha@mail.ru")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstName("Iluya")
//                            .lastName("Kisha")
//                            .birthday(new BirthDate(LocalDate.of(2007, 07, 21)))
//                            .build())
//                    .role(ADMIN)
//                    .company(company)
//                    .projectName("Create landing paje")
//                    .build();
//            session.save(manager);
//
//            session.flush();
//
//            var programme1 = session.get(Programmer.class, 1L);
//            var manager1 = session.get(Manager.class, 2L);
//
//            System.out.println("------------------------/////-------------------------");
//
//            System.out.println(programme1);
//            System.out.println(manager1);
//
//            System.out.println("------------------------/////-------------------------");
//
//            session.getTransaction().commit();
//        }
//
//    }

    @Test
    void checkTestContainers() {
        try (var sessionFactory = HibernateUtilTest.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = Company.builder()
                    .name("Twitter")
                    .build();

            session.saveOrUpdate(company);

            Company company1 = session.get(Company.class, company.getId());

            System.out.println("---------------------------------------------");
            System.out.println(company1);
            System.out.println("---------------------------------------------");

            session.getTransaction().commit();
        }
    }

    @Test
    void checkH2() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = Company.builder()
                    .name("Twitter")
                    .build();

            session.saveOrUpdate(company);

            Company company1 = session.get(Company.class, company.getId());

            System.out.println("---------------------------------------------");
            System.out.println(company1);
            System.out.println("---------------------------------------------");

            session.getTransaction().commit();
        }
    }

    @Test
    void printMapCollection() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = session.get(Company.class, 3);

            Map<String, User> entries = company.getUsers();

            entries.forEach((k, v) -> System.out.println(k + " : " + v));

            session.getTransaction().commit();
        }

    }

//    @Test
//    void checkByMapCollection() {
//        try (var sessionFactory = HibernateUtil.buildSessionFactory();
//        var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            Company company = Company.builder()
//                    .name("Amazon")
//                    .build();
//
//            User user1 = User.builder()
//                    .username("yaha@mail.ru")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstName("Arsen")
//                            .lastName("Minnegulov")
//                            .birthday(new BirthDate(LocalDate.of(2002, 03, 24)))
//                            .build())
//                    .role(ADMIN)
//                    .company(company)
//                    .build();
//
//            User user2 = User.builder()
//                    .username("aha@mail.ru")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstName("Liza")
//                            .lastName("Minnegulova")
//                            .birthday(new BirthDate(LocalDate.of(2003, 11, 14)))
//                            .build())
//                    .role(USER)
//                    .company(company)
//                    .build();
//
//            User user3 = User.builder()
//                    .username("iha@mail.ru")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstName("Ildan")
//                            .lastName("Gala")
//                            .birthday(new BirthDate(LocalDate.of(2001, 05, 9)))
//                            .build())
//                    .role(USER)
//                    .company(company)
//                    .build();
//
//
//            company.addUser(user1);
//            company.addUser(user2);
//            company.addUser(user3);
//
//            session.saveOrUpdate(company);
//
//            session.getTransaction().commit();
//        }
//    }

    @Test
    void orderByCollection() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = session.get(Company.class, 2);
//            Set<User> users = company.getUsers();
//            for (User user : users) {
//                System.out.println(user);
//            }

            session.getTransaction().commit();
        }
    }
//    @Test
//    void saveUserForCompany() {
//        try (var sessionFactory = HibernateUtil.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            Company company = session.get(Company.class, 2);
//
//            User user1 = User.builder()
//                    .username("yaha@mail.ru")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstName("Arsen")
//                            .lastName("Minnegulov")
//                            .birthday(new BirthDate(LocalDate.of(2002, 03, 24)))
//                            .build())
//                    .role(ADMIN)
//                    .company(company)
//                    .build();
//
//            User user2 = User.builder()
//                    .username("aha@mail.ru")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstName("Liza")
//                            .lastName("Minnegulova")
//                            .birthday(new BirthDate(LocalDate.of(2003, 11, 14)))
//                            .build())
//                    .role(USER)
//                    .company(company)
//                    .build();
//
//            User user3 = User.builder()
//                    .username("iha@mail.ru")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstName("Ildan")
//                            .lastName("Gala")
//                            .birthday(new BirthDate(LocalDate.of(2001, 05, 9)))
//                            .build())
//                    .role(USER)
//                    .company(company)
//                    .build();
//
//            company.addUser(user1);
//            company.addUser(user2);
//            company.addUser(user3);
//
////            session.saveOrUpdate(company);
//            session.saveOrUpdate(user1);
//            session.saveOrUpdate(user2);
//            session.saveOrUpdate(user3);
//
//            session.getTransaction().commit();
//        }
//    }

    @Test
    void checkElementCollection() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = session.get(Company.class, 1L);
            LocaleInfo ruLocaleInfo = LocaleInfo.builder()
                    .lang("ru")
                    .description("Русский язык")
                    .build();

            LocaleInfo enLocaleInfo = LocaleInfo.builder()
                    .lang("en")
                    .description("English description")
                    .build();

//            List<LocaleInfo> locales = company.getLocales();
//
//            locales.add(ruLocaleInfo);
//            locales.add(enLocaleInfo);

            session.getTransaction().commit();
        }
    }

//    @Test
//    void checkUsersChat() {
//        try (var sessionFactory = HibernateUtil.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            Company company = session.get(Company.class, 1L);
//
//            Chat chat = Chat.builder()
//                    .name("GPT")
//                    .build();
//
//            User user = User.builder()
//                    .username("arsenminnegulov@gmail.com")
//                    .role(ADMIN)
//                    .personalInfo(PersonalInfo.builder()
//                            .birthday(new BirthDate(LocalDate.of(2002, 03, 24)))
//                            .firstName("Arsen")
//                            .lastName("Minnegulov")
//                            .build())
//                    .company(company)
//                    .build();
//
//
//            UsersChat usersChat = UsersChat.builder()
//                    .chat(chat)
//                    .user(user)
//                    .build();
//
//
//
//            session.saveOrUpdate(chat);
//            session.saveOrUpdate(user);
//            session.saveOrUpdate(usersChat);
//
//
//            session.getTransaction().commit();
//        }
//    }

//    @Test
//    void checkManyToMany() {
//        try (var sessionFactory = HibernateUtil.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            Company company = session.get(Company.class, 1L);
//
//            User user = User.builder()
//                    .username("arsenminnegulov@gmail.com")
//                    .role(ADMIN)
//                    .personalInfo(PersonalInfo.builder()
//                            .birthday(new BirthDate(LocalDate.of(2002, 03, 24)))
//                            .firstName("Arsen")
//                            .lastName("Minnegulov")
//                            .build())
//                    .company(company)
//                    .build();
//
//            Chat chat = Chat.builder()
//                    .name("GPT")
//                    .build();
//
//
//            session.saveOrUpdate(user);
//
//            session.getTransaction().commit();
//        }
//    }

    @Test
    void checkRemoveProfileToUser() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var profile = session.get(Profile.class, 4L);

            session.remove(profile);

            session.getTransaction().commit();
        }
    }

//    @Test
//    void checkOneToOne() {
//        try (var sessionFactory = HibernateUtil.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            Company company = session.get(Company.class, 1);
//
//            User user = User.builder()
//                    .username("sahalysyk05@mail.ru")
//                    .company(company)
//                    .build();
//
//            Profile profile = Profile.builder()
//                    .street("Kommustichesckaya")
//                    .language("ru")
//                    .build();
//
//            profile.setUser(user);
//
//            session.saveOrUpdate(user);
//
//
//            session.getTransaction().commit();
//        }
//    }

    @Test
    void checkOrphanRemoval() {
        @Cleanup
        var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup
        var session = sessionFactory.openSession();

        session.beginTransaction();

        var company = session.get(Company.class, 1);
//        company.getUsers().removeIf(user -> user.getId().equals(1L));

        session.getTransaction().commit();
    }

    @Test
    void checkLazyInitializationException() {
        Company company = null;
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession()) {
            session.beginTransaction();

            company = session.get(Company.class, 1);
//            Set<User> users = company.getUsers();

//            System.out.println(users.size());

            session.getTransaction().commit();
        }
        System.out.println(company);
    }

    @Test
    void deleteCompanyFromUser() {
        @Cleanup
        var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup
        var session = sessionFactory.openSession();

        session.beginTransaction();

//        var company = session.get(Company.class, 1);
        var user = session.get(User.class, 1);

        session.remove(user);

        session.getTransaction().commit();
    }

//    @Test
//    void addUserToCompany() {
//        @Cleanup
//        var sessionFactory = HibernateUtil.buildSessionFactory();
//        @Cleanup
//        var session = sessionFactory.openSession();
//
//        Company company = Company.builder()
//                .name("Yandex")
//                .build();
//
//        User user1 = User.builder()
//                .username("sahalysyk01@mail.ru")
//                .build();
//
//        User user2 = User.builder()
//                .username("sahalysyk02@mail.ru")
//                .build();
//
//        User user3 = User.builder()
//                .username("sahalysyk03@mail.ru")
//                .build();
//
//        company.addUser(user1);
//        company.addUser(user2);
//        company.addUser(user3);
//
//        session.beginTransaction();
//
//        session.saveOrUpdate(company);
//
//        session.saveOrUpdate(user1);
//        session.saveOrUpdate(user2);
//        session.saveOrUpdate(user3);
//
//
//        session.getTransaction().commit();
//    }

//    @Test
//    void checkReflectionApi() {
//        User user = User.builder()
//                .username("sahalysyk@mail.ru")
//                .build();
//
//        String sql = """
//                INSERT INTO
//                %s
//                (%s)
//                VALUES
//                (%s)
//                """;
//
//        String tableName = ofNullable(user.getClass().getAnnotation(Table.class).name())
//                .orElse(user.getClass().getName());
//
//        Field[] fields = user.getClass().getDeclaredFields();
//        String columnName = Arrays.stream(fields)
//                .map(field -> ofNullable(field.getAnnotation(Column.class))
//                        .map(Column::name)
//                        .orElse(field.getName()))
//                .collect(Collectors.joining(", "));
//
//        String values = Arrays.stream(fields)
//                .map(field -> "?")
//                .collect(Collectors.joining(", "));
//
//        String formatted = String.format(sql, tableName, columnName, values);
//
//        System.out.println(formatted);
//    }
}