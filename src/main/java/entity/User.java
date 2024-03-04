package entity;

import domain.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@NamedEntityGraph(
        name = "withCompanyAndChat",
        attributeNodes = {
                @NamedAttributeNode(value = "company"),
                @NamedAttributeNode(value = "usersChats", subgraph = "chats")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "chats",
                        attributeNodes = @NamedAttributeNode("chat"))
        }
)
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
@ToString(exclude = "paymentList")
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

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private Set<UsersChat> usersChats = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "receivers")
//    @Fetch(FetchMode.SUBSELECT)
    private List<Payment> paymentList = new ArrayList<>();

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }
}
