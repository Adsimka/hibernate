package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import static org.hibernate.envers.RelationTargetAuditMode.AUDITED;
import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
//@OptimisticLocking(type = OptimisticLockType.VERSION)
//@DynamicUpdate
@Audited
public class Payment extends AuditableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Version
//    private Long version;

    @Column(nullable = false)
    private Long amount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receivers;
}
