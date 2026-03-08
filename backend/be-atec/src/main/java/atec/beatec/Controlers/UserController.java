
package atec.beatec.Controlers;
import atec.beatec.Entities.Role;
import atec.beatec.Entities.User;
import atec.beatec.Entities.UserDTO;
import atec.beatec.Repositories.UserRepository;
import atec.beatec.Services.IUserService;
import org.springframework.http.HttpStatus;
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

        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Get all users with a given name.
     *
     * @param name Name to search for
     * @return List of users with the specified name
     */
    @GetMapping("/by-name")
    public ResponseEntity<?> getAllUsersByName(@RequestParam String name) {
        /*
        var user = userService.getUserByName(name);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
        */
        return ResponseEntity.ok(userService.getUserByName(name));

    }

    /**
     * updates all user given parameters (besides password)
     * @param id
     * @param name
     * @param level
     * @return
     */
    @PutMapping("/{id}") // post criar put ou patch alterar
    public ResponseEntity<?> AlterById(@PathVariable Long id, @RequestParam String name, @RequestParam int level,@RequestParam String FieldsOfInterest, @RequestParam(defaultValue = "not available") String ProfilePicture) {
        /*try {
            UserDTO user = userService.getUserById(id);
            UserDTO updateUser = userService.updateUser(id, name, level, email);
            return ResponseEntity.ok(updateUser);
        }
        catch(Exception e) {
            return ResponseEntity.notFound().build();
        }*/System.out.println(ProfilePicture);
        UserDTO updatedUser = userService.updateUser(id, name, level,FieldsOfInterest,ProfilePicture);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * updates username
     * @param id
     * @param name
     * @return
     */
    @PutMapping("name/{id}") // post criar put ou patch alterar
    public ResponseEntity<?> AlterByIdname(@PathVariable Long id, @RequestParam String name) {

        try {
            userService.getUserByName(name);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            // Nome disponível
        }

        UserDTO user = userService.getUserById(id);
        UserDTO updatedUser = userService.updateUser(id, name, user.getLevel(), user.getFieldsOfInterest(),user.getProfilePicture());
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * updates user's level
     * @param id
     * @param level
     * @return
     */
    @PutMapping("level/{id}") // post criar put ou patch alterar
    public ResponseEntity<?> AlterByIdlevel(@PathVariable Long id, @RequestParam int level) {
        /*try {
            UserDTO user = userService.getUserById(id);
            UserDTO updateUser = userService.updateUser(id, user.getName(), level, user.getEmail());
            return ResponseEntity.ok(updateUser);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }*/
        UserDTO user = userService.getUserById(id);
        UserDTO updatedUser = userService.updateUser(id, user.getName(), level, user.getFieldsOfInterest(),user.getProfilePicture());
        return ResponseEntity.ok(updatedUser);
    }


    /**
     *
     * @param pageSize size of page
     * @param pageNumber number of users
     * @return all given users in a given page
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam int pageSize,@RequestParam int pageNumber) {
        /*List<UserDTO> allusers= userService.ListAllUsers(pageSize,pageNumber);
        if(allusers.size()==0)
        {
            return ResponseEntity.noContent().build();
        }
        if(allusers==null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allusers);

         */
        List<UserDTO> users = userService.ListAllUsers(pageSize, pageNumber);

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    /**
     * deletes a user with a given id
     * @param id
     * @return sucess mesage "delete" or not found response if the user doesnt exist
     */
    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestParam Long id) {
        /*
        try{
            var user = userService.getUserById(id);
            userService.DeleteUser(id);
            return ResponseEntity.ok("deleted");
        }
        catch(Exception e)
        {
            return ResponseEntity.notFound().build();
        }

         */
            userService.DeleteUser(id);
            return ResponseEntity.noContent().build();
        }





}