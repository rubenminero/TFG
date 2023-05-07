package ruben.TFG.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ruben.TFG.model.Whitelist.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String nif;
    private Role role;
}
