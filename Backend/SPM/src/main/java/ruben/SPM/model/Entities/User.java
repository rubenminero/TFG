package ruben.SPM.model.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ruben.SPM.model.DTO.Entities.UserDTO;
import ruben.SPM.model.JWT_Token.Token;
import ruben.SPM.model.Whitelist.Role;

import java.util.Collection;
import java.util.List;

/**
 * User class, represents a user in the database.
 */
@Entity
@Data
@Builder
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
public class  User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String nif;
    @Email
    @NonNull
    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.username = userDTO.getUsername();
        this.first_name = userDTO.getFirst_name();
        this.last_name = userDTO.getLast_name();
        this.password = userDTO.getPassword();
        this.nif = userDTO.getNif();
        this.email = userDTO.getEmail();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
