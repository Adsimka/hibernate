package entity;

import com.sun.source.tree.Tree;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SortNatural;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(exclude = "users")
@EqualsAndHashCode(of = "name")
@Table(name="companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "company", orphanRemoval = true, cascade = CascadeType.ALL)
    @MapKey(name = "username")
    @OrderBy("username DESC")
    private Map<String, User> users = new HashMap<>();

    @Builder.Default
    @CollectionTable(name = "company_locale", joinColumns = @JoinColumn(name = "company_id"))
    @ElementCollection
    @Column(name = "description")
    @MapKeyColumn(name = "lang")
    private Map<String, String> locales = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUsername(), user);
        user.setCompany(this);
    }
}
