package atec.beatec.Controlers;

import atec.beatec.Entities.UserDTO;
import atec.beatec.Exceptions.UserNotFoundException;
import atec.beatec.Services.IUserService;
import atec.beatec.Services.JWTTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

import static atec.beatec.config.SecurityConstants.JWT_EXPIRATION_TIME;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final IUserService userService;
    private final JWTTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor injection of the UserService.
     *
     * @param userService Service for user operations
     */
    public AuthenticationController(IUserService userService, JWTTokenService jwttokenservice,UserDetailsService userDetailsService) {
        this.jwtTokenService = jwttokenservice;
        this.userService = userService;
        this.userDetailsService=userDetailsService;
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
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {


        try {
            UserDTO userforname=userService.getUserByEmail(email);

           String  name=userforname.getName();
            if (userService.ConfirmPassword(name, password)) {
                //criar token
                //return the reponse com o token
                // Build Authentication object manually
                System.out.println("here");
                name=userforname.getName();
                System.out.println(userforname.getName());

                    UserDetails userDetails = userDetailsService.loadUserByUsername(name);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                name,    // the username
                                null,    // password is not needed after login
                                //Collections.singleton(new SimpleGrantedAuthority("USER")) // empty authorities
                                userDetails.getAuthorities() // ← real role from DB

                        );

                // Generate JWT
                String token = jwtTokenService.generateToken(auth);
                //return ResponseEntity.ok(Map.of("token", token));// Set the token as an HttpOnly cookie
                Cookie cookie = new Cookie("token", token);
                cookie.setHttpOnly(true);
                cookie.setSecure(false);       // use false in local dev if not using HTTPS
                cookie.setPath("/");
                cookie.setMaxAge(3600); // convert ms → seconds
                response.addCookie(cookie);
                UserDTO user=userService.getUserByName(name);

                return ResponseEntity.ok(Map.of("message", user));

            } else {
                // Password incorreta
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "INVALID_CREDENTIALS",
                                "message", "Invalid username or password"));
            }
        }catch(UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "INVALID_CREDENTIALS",
                            "message", "Invalid username or password"));
        }
    }
}
