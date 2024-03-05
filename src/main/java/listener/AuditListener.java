package listener;

import entity.AuditableEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class AuditListener {
    @PrePersist
    public void prePersist(AuditableEntity<?> entity) {
        entity.setCreatedTime(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(AuditableEntity<?> entity) {
        entity.setUpdateTime(LocalDateTime.now());
    }
}
