package ruben.SPM.controllers.EntitiesControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ruben.SPM.model.DTO.Entities.OrganizerDTO;
import ruben.SPM.model.Entities.Organizer;
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
public class OrganizerController {

    private final OrganizerService organizerService;
    private final UserService userService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('organizer:read')")
    @Operation(summary = "Return all the enabled organizers in the database.")
    public ResponseEntity getAllOrganizers() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        List<Organizer> organizers = organizerService.getEnabledOrganizers();
        if (organizers.size() == 0 || organizers == null) {
            String msg = "There is no athletes.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }

        // Convert the list of organizers to a list of OrganizerDTOs
        List<OrganizerDTO> organizerDTOs = organizers.stream().map(OrganizerDTO::new).collect(Collectors.toList());
        log.info("The organizers has successfully been retrieved.");
        return ResponseEntity.ok(organizerDTOs);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('organizer:read')")
    @Operation(summary = "Return the organizer with the id provided.")
    public ResponseEntity getOrganizerById( @PathVariable(name = "id") Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        Organizer organizer = organizerService.getOrganizer(id);
        if (organizer != null) {
            if (organizer.isEnabled()){
                log.info("The organizer has been found");
                return ResponseEntity.ok(new OrganizerDTO(organizer));
            }else {
                String msg = "The organizer that you asked is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
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
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Boolean username_check = userService.validUsername(organizer.getUsername());
        if (username_check){
            String msg = "This username is already taken.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (!id.equals(organizer.getId())) {
            String msg = "Bad request ,the id given in the path doesnt match with the id on the organizer.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        if (organizerService.getOrganizer(id) == null) {
            String msg = "Bad request ,there is no organizer for that id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        log.info("Organizer updated successfully");
        return ResponseEntity.ok(OrganizerDTO.fromOrganizer(organizerService.saveOrganizer(organizer)));
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('organizer:create')")
    @Operation(summary = "Create a organizer with the data provided.")
    public ResponseEntity createOrganizer(@RequestBody Organizer organizer) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Boolean username_check = userService.validUsername(organizer.getUsername());
        if (username_check){
            String msg = "This username is already taken.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        log.info("Organizer created successfully");
        return ResponseEntity.ok(OrganizerDTO.fromOrganizer(organizerService.saveOrganizer(organizer)));
    }





}
