package listener;

import entity.Revision;
import org.hibernate.envers.RevisionListener;

public class UserRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object o) {
        ((Revision) o).setUsername("Arsen");
    }
}
