package ruben.TFG.controllers.EntitiesControllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.model.DTO.Entities.OrganizerDTO;
import ruben.TFG.model.Entities.Organizer;
import ruben.TFG.service.EntitiesServices.OrganizerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/organizers")
@AllArgsConstructor
public class OrganizerController {

    private final OrganizerService organizerService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('organizer:read')")
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


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('organizer:read')")
    public ResponseEntity<OrganizerDTO> getOrganizerById( @PathVariable(name = "id") Long id) {

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
    @PreAuthorize("hasAuthority('organizer:update')")
    public ResponseEntity<OrganizerDTO> updateOrganizer(@PathVariable Long id, @RequestBody Organizer organizer) {
        if (!id.equals(organizer.getId())) {
            log.warn("Bad request , the id given in the path doesnt match with the id on the organizer");
            return ResponseEntity.badRequest().build();
        }
        log.info("Organizer updated successfully");
        return ResponseEntity.ok(OrganizerDTO.fromOrganizer(organizerService.saveOrganizer(organizer)));
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('organizer:create')")
    public ResponseEntity<OrganizerDTO> createOrganizer(@RequestBody Organizer organizer) {
        log.info("Organizer created successfully");
        return ResponseEntity.ok(OrganizerDTO.fromOrganizer(organizerService.saveOrganizer(organizer)));
    }



}
