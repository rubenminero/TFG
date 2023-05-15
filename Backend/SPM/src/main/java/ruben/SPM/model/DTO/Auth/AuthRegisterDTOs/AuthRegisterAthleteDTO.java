package ruben.SPM.model.DTO.Auth.AuthRegisterDTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ruben.SPM.model.Whitelist.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterAthleteDTO {
    private Long id;
    private String username;
    private String first_name;
    private String last_name;
    private String password;
    private String nif;
    private String email;
    private String phone_number;
    private Role role = Role.ATHLETE;
}
