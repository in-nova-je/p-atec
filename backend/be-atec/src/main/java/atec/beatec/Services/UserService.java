
package atec.beatec.Services;
import atec.beatec.Entities.User;
import atec.beatec.Entities.UserDTO;
import atec.beatec.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //or Autowired, Wy im i not using Autowired?
    //    @Autowired


    // Create user
    @Transactional //when should i use it ? (makes the methos calles atomic)
    public UserDTO createUser(String name, int level, String password) {

        //for conditions use a method validator to go allong the solid principles


        User user = new User(name, level, password);

        /*
        funcao de hash a password
        */

        var saved = userRepository.save(user);
        return new UserDTO(saved.getId(), saved.getName(), saved.getLevel());
    }


    // Get user by ID
    public UserDTO getById(Long id) {
        var result =  userRepository.findById(id)
                .orElse(null);

        assert result != null;
        return new UserDTO(result.getId(), result.getName(), result.getLevel());
    }

    // Get all users by name
    public List<UserDTO> getAllByName(String name) {
        return userRepository.findAllByName(name)
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getLevel()))
                .toList();
    }

    public UserDTO updateUser(Long id, String name, int level) {
        User updatedUser= new User(id, name, level,null);
        User saved=userRepository.save(updatedUser);
        return new UserDTO(saved.getId(),saved.getName(),saved.getLevel());
    }

}

