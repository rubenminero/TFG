package ruben.TFG.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.controllers.DTO.Sports_typeDTO;
import ruben.TFG.controllers.DTO.TournamentDTO;
import ruben.TFG.controllers.DTO.UserDTO;
import ruben.TFG.model.Sports_type;
import ruben.TFG.model.Tournament;
import ruben.TFG.model.User;
import ruben.TFG.service.Sports_typeService;
import ruben.TFG.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/sports_types")
public class Sports_typeController {

    private final Sports_typeService sportsTypeService;

    /**
     * Constructor of the class
     * @param sportsTypeService, the service to manage the sports types data
     */
    public Sports_typeController(Sports_typeService sportsTypeService) {
        this.sportsTypeService = sportsTypeService;
    }
        @PostMapping("/")
        @ApiOperation("Create a sport type.")
        public ResponseEntity<Sports_typeDTO> createUser(@ApiParam("New sport type object") @RequestBody Sports_type sport_type) {
            log.info("Sport type created successfully");
            return ResponseEntity.ok(Sports_typeDTO.fromSport_type(sportsTypeService.saveSport_type(sport_type)));
        }
        @PutMapping("/{id}")
        @ApiOperation("Change a sport type by his id.")
        public ResponseEntity<Sports_typeDTO> updateUser(@ApiParam("The id of the sport type")  @PathVariable Long id, @ApiParam("Modified sport_type object") @RequestBody Sports_type sport_type) {
            if (!id.equals(sport_type.getId())) {
                log.warn("Bad request , the id given in the path doesnt match with the id on the sport type");
                return ResponseEntity.badRequest().build();
            }
            log.info("Sport type updated successfully");
            return ResponseEntity.ok(Sports_typeDTO.fromSport_type(sportsTypeService.saveSport_type(sport_type)));
        }
        @GetMapping("/{id}")
        @ApiOperation("Get a sport type by his id")
        public ResponseEntity<Sports_typeDTO> getSport_typeById(@ApiParam("The id of the sport type") @PathVariable(name = "id") Long id) {

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
        @ApiOperation("Get the enabled sport types")
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
