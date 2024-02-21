package entity;

import domain.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Manager extends User {

    private String projectName;

    @Builder
    public Manager(Long id, Profile profile, String username, PersonalInfo personalInfo,
                   Role role, Company company, Set<UsersChat> usersChats, String projectName) {
        super(id, profile, username, personalInfo, role, company, usersChats);
        this.projectName = projectName;
    }
}
