package entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users_chat")
@EqualsAndHashCode(exclude = "user", callSuper=false)
public class UsersChat extends AuditableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    public void setUser(User user) {
        this.user = user;
        this.user.getUsersChats().add(this);
    }
}
