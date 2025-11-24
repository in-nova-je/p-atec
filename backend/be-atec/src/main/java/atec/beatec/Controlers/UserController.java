
package atec.beatec.Controlers;
import atec.beatec.Entities.UserDTO;
import atec.beatec.Services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




/**
 * REST controller for managing User entities.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {


    private final IUserService userService;

    /**
     * Constructor injection of the UserService.
     *
     * @param userService Service for user operations
     */
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user.
     *
     * @param name Name of the user to create
     * @return ResponseEntity with the created User and HTTP status
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestParam String name, @RequestParam int age, @RequestParam String password) {
        var createdUser = userService.createUser(name, age, password);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
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
}