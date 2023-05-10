package ruben.TFG.model.DTO.Auth.AuthRegisterDTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ruben.TFG.model.Entities.Organizer;
import ruben.TFG.model.Entities.User;
import ruben.TFG.model.Whitelist.Role;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterOrganizerDTO {
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String nif;
    private String email;
    private String company_name;
    private String address;
    private Boolean enabled = Boolean.TRUE;
    private Date disabled_at = null;
    private Role role = Role.ORGANIZER;
}
