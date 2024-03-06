package listener;

import entity.Audit;
import entity.Audit.Operation;
import org.hibernate.event.spi.*;


public class AuditTableListener implements PreDeleteEventListener, PreInsertEventListener {

    @Override
    public boolean onPreDelete(PreDeleteEvent event) {
        auditEntity(event, Audit.Operation.DELETE);
        return false;
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        auditEntity(event, Operation.SAVE);
        return false;
    }

    public void auditEntity(AbstractPreDatabaseOperationEvent event, Audit.Operation operation) {
        if (event.getEntity().getClass() != Audit.class) {
            var audit = Audit.builder()
                    .entityName(event.getPersister().getEntityName())
                    .entityContent(event.getEntity().toString())
                    .operation(operation)
                    .build();
            event.getSession().save(audit);
        }
    }
}
