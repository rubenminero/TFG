package ruben.TFG.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.controllers.DTO.OrganizerDTO;
import ruben.TFG.controllers.DTO.Sports_typeDTO;
import ruben.TFG.controllers.DTO.TournamentDTO;
import ruben.TFG.controllers.DTO.UserDTO;
import ruben.TFG.model.Organizer;
import ruben.TFG.model.Sports_type;
import ruben.TFG.model.Tournament;
import ruben.TFG.model.User;
import ruben.TFG.service.AdminService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    /**
     * Constructor of the class.
     * @param adminService, the service to manage the admin data.
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

    @GetMapping("/tournaments")
    @ApiOperation("Get all tournaments for the admin")
    public ResponseEntity<List<TournamentDTO>> getAllTournaments_Admin() {
        List<Tournament> tournaments = adminService.getAllTournaments();
        if (tournaments == null) {
            log.warn("The supervisor is not authorized to get all the tournaments");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Convert the list of tournaments to a list of TournamentDTOs
        List<TournamentDTO> tournamentDTOS = tournaments.stream().map(TournamentDTO::new).collect(Collectors.toList());
        log.info("The supervisor has successfully retrieved all the tournaments");
        return ResponseEntity.ok(tournamentDTOS);
    }

    @DeleteMapping("/tournaments/{id}")
    @ApiOperation("Delete a tournament by its id.Its a soft delete, only makes the tournament disabled.")
    public ResponseEntity<UserDTO> deleteTournament(@ApiParam("Identifier of the tournament") @PathVariable Long id) {
        if (adminService.getTournament(id) == null) {
            log.warn("Bad request to delete a tournament: tournament does not exist");
            return ResponseEntity.notFound().build();
        }
        log.info("Tournament changed successfully");
        adminService.changeStateTournament(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sports_types")
    @ApiOperation("Get all sport types for the admin")
    public ResponseEntity<List<Sports_typeDTO>> getAllSports_types_Admin() {
        List<Sports_type> sportsTypes = adminService.getAllSports_types();
        if (sportsTypes == null) {
            log.warn("The supervisor is not authorized to get all the sport types");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Convert the list of sport types to a list of Sports_typeDTOs
        List<Sports_typeDTO> sportsTypeDTOS = sportsTypes.stream().map(Sports_typeDTO::new).collect(Collectors.toList());
        log.info("The supervisor has successfully retrieved all the sport types");
        return ResponseEntity.ok(sportsTypeDTOS);
    }

    @DeleteMapping("/sports_types/{id}")
    @ApiOperation("Delete a sport type by its id.Its a soft delete, only makes the sport type disabled.")
    public ResponseEntity<Sports_typeDTO> deleteSport_type(@ApiParam("Identifier of the tournament") @PathVariable Long id) {
        if (adminService.getSport_type(id) == null) {
            log.warn("Bad request to delete a sport type: sport type does not exist");
            return ResponseEntity.notFound().build();
        }
        log.info("Sport type changed successfully");
        adminService.changeStateSport_type(id);
        return ResponseEntity.ok().build();
    }

}
