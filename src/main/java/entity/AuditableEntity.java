package entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
public abstract class AuditableEntity <T extends Serializable> implements BaseEntity<T> {
    @Column(name = "created_time", unique = true, nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "created_by", unique = true, nullable = false)
    private String createdBy;


    protected AuditableEntity(LocalDateTime createdTime, String createdBy) {
        this.createdTime = createdTime;
        this.createdBy = createdBy;
    }
}
