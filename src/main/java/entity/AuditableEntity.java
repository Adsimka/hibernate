package entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
