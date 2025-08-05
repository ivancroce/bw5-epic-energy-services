package team6.bw5_epic_energy_services.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "users")

public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "user_id", nullable = false)
    private UUID id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "avatar")
    private String avatar;
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    @ToString.Exclude
    private List<Role> roleList;

    public User(String username, String email, String password, String name, String surname) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        //this.avatar = avatar;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

}
