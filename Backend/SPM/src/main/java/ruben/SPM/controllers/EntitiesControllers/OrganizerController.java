package ruben.SPM.controllers.EntitiesControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ruben.SPM.model.DTO.Auth.PasswordChangeDTO;
import ruben.SPM.model.DTO.Entities.AthleteDTO;
import ruben.SPM.model.DTO.Entities.OrganizerDTO;
import ruben.SPM.model.DTO.Front.EventFrontDTO;
import ruben.SPM.model.DTO.Front.TournamentFrontDTO;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.model.Entities.User;
import ruben.SPM.service.EntitiesServices.OrganizerService;
import ruben.SPM.service.EntitiesServices.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/organizers")
@AllArgsConstructor
@Tag(name="Organizers")
@CrossOrigin(origins = {"http://localhost:4200"})
public class OrganizerController {

    private final OrganizerService organizerService;
    private final UserService userService;

    @GetMapping("/tournaments/{id}")
    @PreAuthorize("hasAuthority('organizer:read')")
    @Operation(summary = "Return all the enabled tournaments for the organizer in the database.")
    public ResponseEntity getTournamentsOrganizer(@PathVariable(name = "id") Long id) {
        List<Tournament> tournaments = organizerService.getTournamentsOrganizer(id);
        if (tournaments.size() == 0 || tournaments == null) {
            String msg = "There is no tournaments.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }

        // Convert the list of organizers to a list of OrganizerDTOs
        List<TournamentFrontDTO> tournamentFrontDTOS = tournaments.stream().map(TournamentFrontDTO::new).collect(Collectors.toList());
        log.info("The tournaments has successfully been retrieved.");
        return ResponseEntity.ok(tournamentFrontDTOS);
    }

    @GetMapping("/events/{id}")
    @PreAuthorize("hasAuthority('organizer:read')")
    @Operation(summary = "Return all the enabled events for the organizer in the database.")
    public ResponseEntity getEventsOrganizer(@PathVariable(name = "id") Long id) {
        List<Tournament> events = organizerService.getEventsOrganizer(id);
        if (events.size() == 0 || events == null) {
            String msg = "There is no events.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }

        // Convert the list of organizers to a list of OrganizerDTOs
        List<EventFrontDTO> eventFrontDTOS = events.stream().map(EventFrontDTO::new).collect(Collectors.toList());
        log.info("The events has successfully been retrieved.");
        return ResponseEntity.ok(eventFrontDTOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('organizer:read')")
    @Operation(summary = "Return the organizer with the id provided.")
    public ResponseEntity getOrganizerById( @PathVariable(name = "id") Long id) {
        Organizer organizer = organizerService.getOrganizer(id);
        if (organizer != null) {
            if (organizer.isEnabled()){
                log.info("The organizer has been found");
                return ResponseEntity.ok(new OrganizerDTO(organizer));
            }else {
                String msg = "The organizer that you asked is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }

        }
        else {
            String msg = "The organizer that you asked doesnt exist.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('organizer:update')")
    @Operation(summary = "Update a organizer with the data provided.")
    public ResponseEntity updateOrganizer(@PathVariable Long id, @RequestBody Organizer organizer) {
        Organizer organizer_saved = this.organizerService.getOrganizer(id);
        if (organizer_saved == null){
            String msg = "There is no user with that data.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Boolean username_check = userService.validUsername(organizer.getUsername());
        if (username_check && !organizer_saved.getUsername().equals(organizer.getUsername())) {
            String msg = "This username is already taken.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        Boolean email_check = userService.validEmail(organizer.getEmail());
        if (email_check && !organizer_saved.getEmail().equals(organizer.getEmail())) {
            String msg = "This email is already taken.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        Boolean company_check = organizerService.validCompany_name(organizer.getCompany());
        if (company_check && !organizer_saved.getCompany().equals(organizer.getCompany())) {
            String msg = "This company name is already taken.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        log.info("The organizer has successfully been updated.");
        return ResponseEntity.ok(OrganizerDTO.fromOrganizer(organizerService.updateOrganizer(organizer,organizer_saved)));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('organizer:delete')")
    @Operation(summary = "Delete the organizer with the id provided.")
    public ResponseEntity deleteOrganizer(@PathVariable Long id) {
        Organizer organizer = organizerService.getOrganizer(id);
        User user = userService.isAuthorized();
        if (organizer == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        if (organizer.getId() != user.getId()){
            String msg = "The id provided doesnt belong to your user.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(msg);
        }
        organizerService.deleteOrganizer(organizer);
        String msg = "The user has been deleted.";
        log.info(msg);
        OrganizerDTO organizerDTO = new OrganizerDTO(organizer);
        return ResponseEntity.ok(organizerDTO);
    }

    @PutMapping("/password")
    @Operation(summary = "Changes the password for the organizer")
    @PreAuthorize("hasAuthority('organizer:update')")
    public ResponseEntity changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO){
        Organizer organizer = this.organizerService.getOrganizer(passwordChangeDTO.getId_user());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Organizer organizer_logged = this.organizerService.getOrganizerByUsername(username);

        if (organizer != null && organizer_logged.getId().equals(organizer.getId())){
            if (passwordChangeDTO.getPassword().equals(passwordChangeDTO.getConfirmpassword())){
                organizer = this.organizerService.setPasswordHashed(organizer, passwordChangeDTO.getPassword());
                this.organizerService.updateOrganizer(organizer);
                if (organizer != null ){
                    String msg = "The password has been changed.";
                    log.info(msg);
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(msg);
                }else{
                    String msg = "The password has not been changed.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(msg);
                }
            }else{
                String msg = "The passwords provided doesnt match.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(msg);
            }
        }else {
            String msg = "The user with the id provided doesnt exist or doesnt match with the user logged.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
    }
}
