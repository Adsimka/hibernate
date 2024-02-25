package entity;

import domain.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Programmer extends User {
    @Enumerated(EnumType.STRING)
    private Language language;

    @Builder
    public Programmer(Long id, Profile profile, String username, PersonalInfo personalInfo, Role role, Company company, Set<UsersChat> usersChats, Language language, List<Payment> payment) {
        super(id, profile, username, personalInfo, role, company, usersChats, payment);
        this.language = language;
    }
}
