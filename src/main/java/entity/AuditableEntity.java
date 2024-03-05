package entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditableEntity <T extends Serializable> implements BaseEntity<T> {

    private LocalDateTime createdTime;
    private String createdBy;

    private LocalDateTime updateTime;
    private String updateBy;

    @PrePersist
    public void prePersist() {
        setCreatedTime(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        setUpdateTime(LocalDateTime.now());
    }
}
