package ruben.TFG.controllers.EntitiesControllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.model.DTO.Entities.Sports_typeDTO;
import ruben.TFG.model.Entities.Sports_type;
import ruben.TFG.service.EntitiesServices.Sports_typeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/sports_types")
@AllArgsConstructor
public class Sports_typeController {

    private final Sports_typeService sportsTypeService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<Sports_typeDTO> createSport_Type(@RequestBody Sports_type sport_type) {
        log.info("Sport type created successfully");
        return ResponseEntity.ok(Sports_typeDTO.fromSport_type(sportsTypeService.saveSport_type(sport_type)));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<Sports_typeDTO> updateSport_Type(@PathVariable Long id,  @RequestBody Sports_type sport_type) {
        if (!id.equals(sport_type.getId())) {
            log.warn("Bad request , the id given in the path doesnt match with the id on the sport type");
            return ResponseEntity.badRequest().build();
        }
        log.info("Sport type updated successfully");
        return ResponseEntity.ok(Sports_typeDTO.fromSport_type(sportsTypeService.saveSport_type(sport_type)));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('athletes:read')")
    public ResponseEntity<Sports_typeDTO> getSport_typeById( @PathVariable(name = "id") Long id) {

        Sports_type sportType = sportsTypeService.getSport_type(id);
        if (sportType != null) {
            log.info("The sport type has been found");
            return ResponseEntity.ok(new Sports_typeDTO(sportType));
        }
        else {
            log.warn("The sport type by id has not been found");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('athletes:read')")
    public ResponseEntity<List<Sports_typeDTO>> getAllSports_types() {
        List<Sports_type> sportsTypes = sportsTypeService.getEnabledSport_types();
        if (sportsTypes == null) {
            log.warn("The supervisor is not authorized to get all the sport types");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Convert the list of sport types to a list of Sports_typeDTOs
        List<Sports_typeDTO> sportsTypeDTOS = sportsTypes.stream().map(Sports_typeDTO::new).collect(Collectors.toList());
        log.info("The supervisor has successfully retrieved all the sport types");
        return ResponseEntity.ok(sportsTypeDTOS);
    }
}
