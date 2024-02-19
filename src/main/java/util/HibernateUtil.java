package util;

import converter.BirthDateConverter;
import entity.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfguration();
            configuration.configure();

        return configuration.buildSessionFactory();
    }

    public static Configuration buildConfguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAttributeConverter(BirthDateConverter.class, true);
        return configuration;
    }
}
