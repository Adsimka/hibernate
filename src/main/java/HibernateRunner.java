import domain.Role;
import entity.Payment;
import entity.User;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import util.HibernateUtil;
import util.TestDataImporter;

import java.sql.SQLException;

@Slf4j
public class HibernateRunner {
    @Transactional
    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            AuditReader auditReader = AuditReaderFactory.get(session);
            User user = auditReader.find(User.class, 1L, 1L);

            session.replicate(user, ReplicationMode.OVERWRITE);

            session.getTransaction().commit();
        }
    }
}
