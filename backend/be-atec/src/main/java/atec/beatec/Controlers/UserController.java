
package atec.beatec.Controlers;
import atec.beatec.Entities.UserDTO;
import atec.beatec.Services.IUserService;
import atec.beatec.Services.JWTTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * REST controller for managing User entities.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {


    private final IUserService userService;
    private final JWTTokenService jwtTokenService;
    /**
     * Constructor injection of the UserService.
     *
     * @param userService Service for user operations
     */
    public UserController(IUserService userService ,JWTTokenService jwttokenservice) {
        this.jwtTokenService=jwttokenservice;
        this.userService = userService;
    }

    /**
     * Create a new user.
     *
     * @param name Name of the user to create
     * @return ResponseEntity with the created User and HTTP status
     */
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestParam String name, @RequestParam int level, @RequestParam String password) {

        var createdUser = userService.createUser(name, level, password);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String name, @RequestParam String password) {
        if (userService.ConfirmPassword(name, password)){
            //criar token
            //return the reponse com o token
            // Build Authentication object manually
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            name,    // the username
                            null,    // password is not needed after login
                            Collections.singleton(new SimpleGrantedAuthority("USER")) // empty authorities
                    );

            // Generate JWT
            String token = jwtTokenService.generateToken(auth);
            Map<String, String> response = Map.of("token", token);
            return  ResponseEntity.ok(response);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get a user by ID.
     *
     * @param id User ID
     * @return ResponseEntity with the User if found, or 404 if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        var user = userService.getById(id);
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
    public ResponseEntity<?> getAllByName(@RequestParam String name) {
        var users = userService.getAllByName(name);
        return ResponseEntity.ok(users);
    }


    @PutMapping("/{id}") // post criar put ou patch alterar
    public ResponseEntity<?> AlterById(@PathVariable Long id, @RequestParam String name, @RequestParam int level) {
        var user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDTO updateUser = userService.updateUser(id, name, level);
        return ResponseEntity.ok(updateUser);
    }

    /**
     *
     * @param pageSize size of page
     * @param pageNumber number of users
     * @return all given users in a given page
     */
    @GetMapping("/allusers")
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
     *
     * @param id
     * @return sucess mesage "delete" or not found response if the user doesnt exist
     */
    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestParam Long id) {
        var user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.DeleteUser(id);
        return ResponseEntity.ok("deleted");
    }

}