package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@OptimisticLocking(type = OptimisticLockType.VERSION)
//@DynamicUpdate
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false)
    private Long amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_id")
    private User receivers;
}
