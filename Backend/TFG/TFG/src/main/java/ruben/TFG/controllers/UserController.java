package ruben.TFG.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import ruben.TFG.controllers.DTO.InscriptionDTO;
import ruben.TFG.controllers.DTO.UserDTO;
import ruben.TFG.controllers.DTO.WatchlistDTO;
import ruben.TFG.model.Inscription;
import ruben.TFG.model.User;
import ruben.TFG.model.Watchlist;
import ruben.TFG.service.InscriptionService;
import ruben.TFG.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import lombok.extern.slf4j.Slf4j;
import ruben.TFG.service.WatchlistService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final InscriptionService inscriptionService;
    private final WatchlistService watchlistService;
    /**
     * Constructor of the class
     * @param userService, the service to manage the user's data
     * @param inscriptionService, the service to manage the inscription's data
     * @param watchlistService, the service to manage the watchlist's data
     */
    public UserController(UserService userService, InscriptionService inscriptionService, WatchlistService watchlistService) {
        this.userService = userService;
        this.inscriptionService = inscriptionService;
        this.watchlistService = watchlistService;
    }

    @GetMapping("")
    @ApiOperation("Get all users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getEnabledUsers();
        if (users == null) {
            log.warn("There is no users available.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Convert the list of users to a list of UserDTOs
        List<UserDTO> userDTOs = users.stream().map(UserDTO::new).collect(Collectors.toList());
        log.info("The users has successfully been retrieved.");
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a user by his id")
    public ResponseEntity<UserDTO> getUserById(@ApiParam("The id of the user") @PathVariable(name = "id") Long id) {

        User user = userService.getUser(id);
        if (user != null) {
            log.info("The user has been found");
            return ResponseEntity.ok(new UserDTO(user));
        }
        else {
            log.warn("The user by id has not been found");
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    @ApiOperation("Change a user by his id.")
    public ResponseEntity<UserDTO> updateUser(@ApiParam("The id of the user")  @PathVariable Long id, @ApiParam("Modified user object") @RequestBody User user) {
        if (!id.equals(user.getId())) {
            log.warn("Bad request , the id given in the path doesnt match with the id on the user");
            return ResponseEntity.badRequest().build();
        }
        log.info("User updated successfully");
        return ResponseEntity.ok(UserDTO.fromUser(userService.saveUser(user)));
    }

    @PostMapping("")
    @ApiOperation("Create a user.")
    public ResponseEntity<UserDTO> createUser(@ApiParam("Modified user object") @RequestBody User user) {
        log.info("User created successfully");
        return ResponseEntity.ok(UserDTO.fromUser(userService.saveUser(user)));
    }

    @GetMapping("/inscriptions/{id}")
    @ApiOperation("Get the inscriptions for this user")
    public ResponseEntity<List<InscriptionDTO>> getInscriptionById(@ApiParam("The id of the user") @PathVariable(name = "id") Long id) {

        List<Inscription> inscriptions = inscriptionService.getEnabledInscriptions(id);
        if (inscriptions == null) {
            log.info("There is not inscriptions");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        else {
            // Convert the list of inscriptions to a list of inscriptionsDTO
            List<InscriptionDTO> inscriptionDTOS = inscriptions.stream().map(InscriptionDTO::new).collect(Collectors.toList());
            log.info("The inscriptions has successfully been retrieved.");
            return ResponseEntity.ok(inscriptionDTOS);
        }
    }

    @GetMapping("/watchlists/{id}")
    @ApiOperation("Get the watchlists for this user")
    public ResponseEntity<List<WatchlistDTO>> getWatchlistById(@ApiParam("The id of the user") @PathVariable(name = "id") Long id) {

        List<Watchlist> watchlists = watchlistService.getEnabledWatchlists(id);
        if (watchlists == null) {
            log.info("There is not watchlists");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        else {
            // Convert the list of watchlists to a list of watchlistDTOs
            List<WatchlistDTO> watchlistDTOs = watchlists.stream().map(WatchlistDTO::new).collect(Collectors.toList());
            log.info("The watchlists has successfully been retrieved.");
            return ResponseEntity.ok(watchlistDTOs);
        }
    }



}
