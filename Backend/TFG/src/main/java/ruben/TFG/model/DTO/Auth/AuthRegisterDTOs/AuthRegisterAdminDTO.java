package ruben.TFG.model.DTO.Auth.AuthRegisterDTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ruben.TFG.model.Whitelist.Role;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterAdminDTO {
    private Long id;
    private String username;
    private String first_name;
    private String last_name;
    private String password;
    private String nif;
    private String email;
    private Date valid_from = new Date();
    private Role role = Role.ADMIN;
}
