package ruben.TFG.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import ruben.TFG.controllers.DTO.UserDTO;
import ruben.TFG.model.User;
import ruben.TFG.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor of the class
     * @param userService, the service to manage the user's data
     */
    public UserController(UserService userService) {
        this.userService = userService;
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
    @GetMapping("/admin")
    @ApiOperation("Get all users for the admin")
    public ResponseEntity<List<UserDTO>> getAllUsers_Admin() {
        List<User> users = userService.getAllUsers();
        if (users == null) {
            log.warn("The supervisor is not authorized to get all the users");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Convert the list of users to a list of UserDTOs
        List<UserDTO> userDTOs = users.stream().map(UserDTO::new).collect(Collectors.toList());
        log.info("The supervisor has successfully retrieved all the users");
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
    @DeleteMapping("/{id}")
    @ApiOperation("Delete a user by its id.Its a soft delete, only makes the user disabled.")
    public ResponseEntity<UserDTO> deleteUser(@ApiParam("Identifier of the user") @PathVariable Long id) {
        if (userService.getUser(id) == null) {
            log.warn("Bad request to delete a user: user does not exist");
            return ResponseEntity.notFound().build();
        }
        log.info("User disabled successfully");
        userService.disableUser(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("")
    @ApiOperation("Create a user.")
    public ResponseEntity<UserDTO> createUser(@ApiParam("Modified user object") @RequestBody User user) {
        log.info("User created successfully");
        return ResponseEntity.ok(UserDTO.fromUser(userService.saveUser(user)));
    }



}
