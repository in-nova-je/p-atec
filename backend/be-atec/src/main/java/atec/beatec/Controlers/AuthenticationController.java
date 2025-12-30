package atec.beatec.Controlers;

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
    public AuthenticationController(IUserService userService ,JWTTokenService jwttokenservice) {
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
        if (userService.getUserByName(name) != null) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
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
}
