
package atec.beatec.Services;
import atec.beatec.Entities.User;
import atec.beatec.Entities.UserDTO;
import atec.beatec.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;


    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //or Autowired, Wy im i not using Autowired?
    //    @Autowired


    // Create user
    @Transactional //when should i use it ? (makes the methos calles atomic)
    public UserDTO createUser(String name, int level, String password) {

        //for conditions use a method validator to go allong the solid principles


        User user = new User(name, level,passwordEncoder.encode(password));

        /*
        funcao de hash a password
        */


        var saved = userRepository.save(user);
        return new UserDTO(saved.getId(), saved.getName(), saved.getLevel());
    }


    boolean UserWithSameNameExists(String name) {
        if(userRepository.findByName(name)!=null) {
            return true;
        }
        else {
            return false;
        }
    }


    // Get user by ID
    public UserDTO getById(Long id) {
        var result =  userRepository.findById(id)
                .orElse(null);

        assert result != null;
        return new UserDTO(result.getId(), result.getName(), result.getLevel());
    }

    public UserDTO getByName(String name) {
       try {
           var user = userRepository.findByName(name);
           return new UserDTO(user.getId(), user.getName(), user.getLevel());
       }catch (Exception e) {return null; }



    }

    // Get all users by name
    public List<UserDTO> getAllByName(String name) {
        return userRepository.findAllByName(name)
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getLevel()))
                .toList();
    }


    //updates user fields
    public UserDTO updateUser(Long id, String name, int level) {
        User updatedUser= new User(id, name, level,null);
        User saved=userRepository.save(updatedUser);
        return new UserDTO(saved.getId(),saved.getName(),saved.getLevel());
    }

    //confirms that user password is correct

    public boolean ConfirmPassword(String name, String password) {//no futuro vai ser hash da password
        var user = userRepository.findByName(name);
        return passwordEncoder.matches(password, user.getPassword());
    }




}

