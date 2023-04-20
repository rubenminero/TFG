package ruben.TFG.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.controllers.DTO.OrganizerDTO;
import ruben.TFG.model.Organizer;
import ruben.TFG.service.OrganizerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/organizers")
public class OrganizerController {

    private final OrganizerService organizerService;

    /**
     * Constructor of the class.
     * @param organizerService, the service to manage the organizer's data.
     */
    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @GetMapping("")
    @ApiOperation("Get all organizers")
    public ResponseEntity<List<OrganizerDTO>> getAllOrganizers() {
        List<Organizer> organizers = organizerService.getEnabledOrganizers();
        if (organizers == null) {
            log.warn("There is no organizers available.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Convert the list of organizers to a list of OrganizerDTOs
        List<OrganizerDTO> organizerDTOs = organizers.stream().map(OrganizerDTO::new).collect(Collectors.toList());
        log.info("The organizers has successfully been retrieved.");
        return ResponseEntity.ok(organizerDTOs);
    }
    @GetMapping("/admin")
    @ApiOperation("Get all organizers for the admin")
    public ResponseEntity<List<OrganizerDTO>> getAllOrganizers_Admin() {
        List<Organizer> organizers = organizerService.getAllOrganizers();
        if (organizers == null) {
            log.warn("The supervisor is not authorized to get all the organizers");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Convert the list of organizers to a list of OrganizerDTOs
        List<OrganizerDTO> userDTOs = organizers.stream().map(OrganizerDTO::new).collect(Collectors.toList());
        log.info("The supervisor has successfully retrieved all the organizers");
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a organizer by his id")
    public ResponseEntity<OrganizerDTO> getOrganizerById(@ApiParam("The id of the organizer") @PathVariable(name = "id") Long id) {

        Organizer organizer = organizerService.getOrganizer(id);
        if (organizer != null) {
            log.info("The organizer has been found");
            return ResponseEntity.ok(new OrganizerDTO(organizer));
        }
        else {
            log.warn("The organizer has not been found");
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    @ApiOperation("Change a organizer by his id.")
    public ResponseEntity<OrganizerDTO> updateOrganizer(@ApiParam("The id of the organizer")  @PathVariable Long id, @ApiParam("Modified organizer object") @RequestBody Organizer organizer) {
        if (!id.equals(organizer.getId())) {
            log.warn("Bad request , the id given in the path doesnt match with the id on the organizer");
            return ResponseEntity.badRequest().build();
        }
        log.info("Organizer updated successfully");
        return ResponseEntity.ok(OrganizerDTO.fromOrganizer(organizerService.saveOrganizer(organizer)));
    }
    @DeleteMapping("/{id}")
    @ApiOperation("Delete a organizer by its id.Its a soft delete, only makes the organizer disabled.")
    public ResponseEntity<OrganizerDTO> deleteOrganizer(@ApiParam("Identifier of the organizer") @PathVariable Long id) {
        if (organizerService.getOrganizer(id) == null) {
            log.warn("Bad request to delete a organizer: organizer does not exist");
            return ResponseEntity.notFound().build();
        }
        log.info("Organizer disabled successfully");
        organizerService.disableOrganizer(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("")
    @ApiOperation("Create a organizer.")
    public ResponseEntity<OrganizerDTO> createOrganizer(@ApiParam("Modified organizer object") @RequestBody Organizer organizer) {
        log.info("Organizer created successfully");
        return ResponseEntity.ok(OrganizerDTO.fromOrganizer(organizerService.saveOrganizer(organizer)));
    }



}