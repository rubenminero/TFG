package ruben.TFG.controllers.EntitiesControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.model.DTO.Entities.OrganizerDTO;
import ruben.TFG.model.DTO.Entities.Sports_typeDTO;
import ruben.TFG.model.Entities.Sports_type;
import ruben.TFG.model.Entities.User;
import ruben.TFG.service.EntitiesServices.Sports_typeService;
import ruben.TFG.service.EntitiesServices.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/sports_types")
@AllArgsConstructor
@Tag(name="Sports types")
public class Sports_typeController {

    private final Sports_typeService sportsTypeService;
    private final UserService userService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('admin:create')")
    @Operation(summary = "Create a sport type with the data provided.")
    public ResponseEntity createSport_Type(@RequestBody Sports_type sport_type) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        log.info("Sport type created successfully");
        return ResponseEntity.ok(Sports_typeDTO.fromSport_type(sportsTypeService.saveSport_type(sport_type)));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(summary = "Update a sport type with the data provided.")
    public ResponseEntity updateSport_Type(@PathVariable Long id,  @RequestBody Sports_type sport_type) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (!id.equals(sport_type.getId())) {
            String msg = "Bad request ,the id given in the path doesnt match with the id on the organizer.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        log.info("Sport type updated successfully");
        return ResponseEntity.ok(Sports_typeDTO.fromSport_type(sportsTypeService.saveSport_type(sport_type)));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return the sport type with the id provided.")
    public ResponseEntity getSport_typeById( @PathVariable(name = "id") Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Sports_type sportType = sportsTypeService.getSport_type(id);
        if (sportType != null) {
            if (sportType.isEnabled()){
                log.info("The sport type has been found");
                return ResponseEntity.ok(new Sports_typeDTO(sportType));
            }else {
                String msg = "The sport type that you asked is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(msg);
            }
        }
        else {
            String msg = "The sport type that you asked doesnt exist.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(msg);
        }
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return all the enabled sports types.")
    public ResponseEntity getAllSports_types() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        List<Sports_type> sportsTypes = sportsTypeService.getEnabledSport_types();
        if (sportsTypes.size() == 0 || sportsTypes == null) {
            String msg = "There is no sports types.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        // Convert the list of sport types to a list of Sports_typeDTOs
        List<Sports_typeDTO> sportsTypeDTOS = sportsTypes.stream().map(Sports_typeDTO::new).collect(Collectors.toList());
        log.info("The sports types has been retrieved.");
        return ResponseEntity.ok(sportsTypeDTOS);
    }
}
