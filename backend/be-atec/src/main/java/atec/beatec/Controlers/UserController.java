
package atec.beatec.Controlers;
import atec.beatec.Entities.UserDTO;
import atec.beatec.Services.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for managing User entities.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {


    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Get a user by ID.
     *
     * @param id User ID
     * @return ResponseEntity with the User if found, or 404 if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        var user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Get all users with a given name.
     *
     * @param name Name to search for
     * @return List of users with the specified name
     */
    @GetMapping("/by-name")
    public ResponseEntity<?> getAllUsersByName(@RequestParam String name) {
        var user = userService.getUserByName(name);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * updates all user given parameters (besides password)
     * @param id
     * @param name
     * @param level
     * @return
     */
    @PutMapping("/{id}") // post criar put ou patch alterar
    public ResponseEntity<?> AlterById(@PathVariable Long id, @RequestParam String name, @RequestParam int level, @RequestParam String email) {
        UserDTO user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDTO updateUser = userService.updateUser(id, name, level,email);
        return ResponseEntity.ok(updateUser);
    }

    /**
     * updates username
     * @param id
     * @param name
     * @return
     */
    @PutMapping("name/{id}") // post criar put ou patch alterar
    public ResponseEntity<?> AlterByIdname(@PathVariable Long id, @RequestParam String name) {
        UserDTO user = userService.getUserById(id);
        UserDTO namecheck= userService.getUserByName(name);
        if(!(namecheck==null))
        {
            return ResponseEntity.notFound().build(); // not allowed
        }
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDTO updateUser = userService.updateUser(id, name, user.getLevel(),user.getEmail());
        return ResponseEntity.ok(updateUser);
    }

    /**
     * updates user's level
     * @param id
     * @param level
     * @return
     */
    @PutMapping("level/{id}") // post criar put ou patch alterar
    public ResponseEntity<?> AlterByIdlevel(@PathVariable Long id, @RequestParam int level) {
        UserDTO user = userService.getUserById(id);


        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDTO updateUser = userService.updateUser(id, user.getName(),level, user.getEmail());
        return ResponseEntity.ok(updateUser);
    }


    /**
     *
     * @param pageSize size of page
     * @param pageNumber number of users
     * @return all given users in a given page
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam int pageSize,@RequestParam int pageNumber) {
        List<UserDTO> allusers= userService.ListAllUsers(pageSize,pageNumber);
        if(allusers.size()==0)
        {
            return ResponseEntity.noContent().build();
        }
        if(allusers==null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allusers);
    }

    /**
     * deletes a user with a given id
     * @param id
     * @return sucess mesage "delete" or not found response if the user doesnt exist
     */
    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestParam Long id) {
        var user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.DeleteUser(id);
        return ResponseEntity.ok("deleted");
    }

}