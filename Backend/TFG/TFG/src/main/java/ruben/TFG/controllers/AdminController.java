package ruben.TFG.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.controllers.DTO.OrganizerDTO;
import ruben.TFG.controllers.DTO.UserDTO;
import ruben.TFG.model.Organizer;
import ruben.TFG.model.User;
import ruben.TFG.service.AdminService;
import ruben.TFG.service.OrganizerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    /**
     * Constructor of the class.
     * @param adminService, the service to manage the admins's data.
     */
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/organizers")
    @ApiOperation("Get all organizers for the admin")
    public ResponseEntity<List<OrganizerDTO>> getAllOrganizers_Admin() {
        List<Organizer> organizers = adminService.getAllOrganizers();
        if (organizers == null) {
            log.warn("The supervisor is not authorized to get all the organizers");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Convert the list of organizers to a list of OrganizerDTOs
        List<OrganizerDTO> userDTOs = organizers.stream().map(OrganizerDTO::new).collect(Collectors.toList());
        log.info("The supervisor has successfully retrieved all the organizers");
        return ResponseEntity.ok(userDTOs);
    }
    @DeleteMapping("/organizers/{id}")
    @ApiOperation("Delete a organizer by its id.Its a soft delete, only makes the organizer disabled.")
    public ResponseEntity<OrganizerDTO> deleteOrganizer(@ApiParam("Identifier of the organizer") @PathVariable Long id) {
        if (adminService.getOrganizer(id) == null) {
            log.warn("Bad request to delete a organizer: organizer does not exist");
            return ResponseEntity.notFound().build();
        }
        log.info("Organizer changed successfully");
        adminService.changeStateOrganizer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @ApiOperation("Get all users for the admin")
    public ResponseEntity<List<UserDTO>> getAllUsers_Admin() {
        List<User> users = adminService.getAllUsers();
        if (users == null) {
            log.warn("The supervisor is not authorized to get all the users");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Convert the list of users to a list of UserDTOs
        List<UserDTO> userDTOs = users.stream().map(UserDTO::new).collect(Collectors.toList());
        log.info("The supervisor has successfully retrieved all the users");
        return ResponseEntity.ok(userDTOs);
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation("Delete a user by its id.Its a soft delete, only makes the user disabled.")
    public ResponseEntity<UserDTO> deleteUser(@ApiParam("Identifier of the user") @PathVariable Long id) {
        if (adminService.getUser(id) == null) {
            log.warn("Bad request to delete a user: user does not exist");
            return ResponseEntity.notFound().build();
        }
        log.info("User changed successfully");
        adminService.changeStateUser(id);
        return ResponseEntity.ok().build();
    }
}
