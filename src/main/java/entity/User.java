package entity;

import domain.BirthDate;
import domain.Role;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.engine.internal.StatefulPersistenceContext;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.persister.entity.EntityPersister;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@FetchProfile(name = "withCompanyAndPayments", fetchOverrides = {
        @FetchProfile.FetchOverride(
                entity = User.class, association = "company", mode = FetchMode.JOIN
        ),
        @FetchProfile.FetchOverride(
                entity = User.class, association = "paymentList", mode = FetchMode.SUBSELECT
        )
})
@NamedQuery(name = "findUserByName", query = "select u from User u join u.company c where u.username = :username and c.name = :companyName")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "company")
@EqualsAndHashCode(exclude = "paymentList")
@Table(name = "users")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@BatchSize(size = 3)
@Builder
public class User implements Comparable<User> {

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
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
//    @Fetch(FetchMode.JOIN)
    private Company company;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UsersChat> usersChats = new HashSet<>();

    @OneToMany(mappedBy = "receivers")
//    @Fetch(FetchMode.SUBSELECT)
    private List<Payment> paymentList = new ArrayList<>();

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }
}
