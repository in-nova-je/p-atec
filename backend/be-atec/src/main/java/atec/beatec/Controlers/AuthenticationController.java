package atec.beatec.Controlers;

import atec.beatec.Entities.UserDTO;
import atec.beatec.Exceptions.UserNotFoundException;
import atec.beatec.Services.IUserService;
import atec.beatec.Services.JWTTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final IUserService userService;
    private final JWTTokenService jwtTokenService;

    /**
     * Constructor injection of the UserService.
     *
     * @param userService Service for user operations
     */
    public AuthenticationController(IUserService userService, JWTTokenService jwttokenservice) {
        this.jwtTokenService = jwttokenservice;
        this.userService = userService;
    }

    /**
     * Create a new user.
     *
     * @param name Name of the user to create
     * @return ResponseEntity with the created User and HTTP status
     */
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestParam String name, @RequestParam int level,
            @RequestParam String password, @RequestParam String email, @RequestParam String FieldsOfInterest,
            @RequestParam(defaultValue = "not available") String Profilepicture) {
        try {
            userService.getUserByName(name);
            // Se chegou aqui, user existe
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "USER_ALREADY_EXISTS",
                            "message", "User already exists with name: " + name));
        } catch (UserNotFoundException e) {

        }
        try {
            userService.getUserByEmail(email);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "EMAIL_ALREADY_IN_USE",
                            "message", "User with given email already exists: " + email));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "MULTIPLE_EMAIL_INSTANCES",
                            "message", "Email appears multiple times: " + email));
        } catch (UserNotFoundException e) {

        }

        UserDTO createdUser = userService.createUser(name, level, password, email, FieldsOfInterest, Profilepicture);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            if (userService.confirmPasswordByEmail(email, password)) {

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        Collections.singleton(new SimpleGrantedAuthority("USER")));

                String token = jwtTokenService.generateToken(auth);
                return ResponseEntity.ok(Map.of("token", token));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "INVALID_CREDENTIALS",
                            "message", "Invalid email or password"));

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "INVALID_CREDENTIALS",
                            "message", "Invalid email or password"));
        }
    }
}
