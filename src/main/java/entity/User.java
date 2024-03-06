package entity;

import domain.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.envers.RelationTargetAuditMode.AUDITED;
import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@NamedQuery(name = "findUserByName", query = "select u from User u join u.company c where u.username = :username and c.name = :companyName")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "paymentList")
@EqualsAndHashCode(exclude = "usersChats")
@Table(name = "users")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@BatchSize(size = 3)
@Builder
@Audited(targetAuditMode = NOT_AUDITED)
public class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    @OneToOne(
//            cascade = CascadeType.ALL,
//            mappedBy = "user",
//            fetch = FetchType.LAZY)
//    private Profile profile;

    @Column(unique = true)
    private String username;

    @Embedded
    @AttributeOverride(name = "birthday", column = @Column(name = "birth_day"))
    private PersonalInfo personalInfo;


    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
//    @Fetch(FetchMode.JOIN)
    private Company company;

    @NotAudited
    @Builder.Default
    @OneToMany(mappedBy = "user")
    private Set<UsersChat> usersChats = new HashSet<>();

    @NotAudited
    @Builder.Default
    @OneToMany(mappedBy = "receivers")
//    @Fetch(FetchMode.SUBSELECT)
    private List<Payment> paymentList = new ArrayList<>();

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }
}
