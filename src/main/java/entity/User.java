package entity;

import domain.BirthDate;
import domain.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.Type;
import org.hibernate.engine.internal.StatefulPersistenceContext;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.persister.entity.EntityPersister;

import java.util.HashSet;
import java.util.Set;

@NamedQuery(name = "findUserByName", query = "select u from User u join u.company c where u.username = :username and c.name = :companyName")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "company")
@EqualsAndHashCode(of = "username")
@Table(name = "users")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements Comparable<User>, BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL,
            mappedBy = "user")
    private Profile profile;

    @Column(unique = true)
    private String username;

    @Embedded
    @AttributeOverride(name = "birthday", column = @Column(name = "birth_day"))
    private PersonalInfo personalInfo;


    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(optional = false,
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    private Company company;

//    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UsersChat> usersChats = new HashSet<>();

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }
}
