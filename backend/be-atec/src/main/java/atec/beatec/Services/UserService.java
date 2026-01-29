
package atec.beatec.Services;
import atec.beatec.Entities.User;
import atec.beatec.Entities.UserDTO;
import atec.beatec.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    // Create user
    @Transactional //when should i use it ? (makes the methos calles atomic)
    public UserDTO createUser(String name, int level, String password,String email) {

        //for conditions use a method validator to go allong the solid principles
        User user = new User(name, level,passwordEncoder.encode(password),email,IsStudentOrNot(email));//hashing
        var saved = userRepository.save(user);
        return new UserDTO(saved.getId(), saved.getName(), saved.getLevel(), saved.getEmail(), saved.getisStudent());
    }
    // Get user by ID
    public UserDTO getUserById(Long id) {
        var result =  userRepository.findById(id)
                .orElse(null);

        assert result != null;
        return new UserDTO(result.getId(), result.getName(), result.getLevel(),result.getEmail(), result.getisStudent());
    }

    public UserDTO getUserByName(String name) {
       try {
           var user = userRepository.findByName(name);
           return new UserDTO(user.getId(), user.getName(), user.getLevel(), user.getEmail(), user.getisStudent());
       }catch (Exception e) {return null; }

    }
    //updates user fields
    public UserDTO updateUser(Long id, String name, int level,String email) {
        User updatedUser= new User(id, name, level,null,email,IsStudentOrNot(email));
        User saved=userRepository.save(updatedUser);
        return new UserDTO(saved.getId(),saved.getName(),saved.getLevel(),saved.getEmail(),saved.getisStudent());
    }
    //confirms that user password is correct
    public boolean ConfirmPassword(String name, String password) {//no futuro vai ser hash da password
        var user = userRepository.findByName(name);
        return passwordEncoder.matches(password, user.getPassword());
    }
    @Override
    public List<UserDTO> ListAllUsers(int pageSize,int pageNumber) {
        Page<User> page = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<User> users = page.getContent();
        List<UserDTO> userDTOs=new ArrayList<>();
        users.forEach(user -> userDTOs.add(new UserDTO(user.getId(), user.getName(), user.getLevel(), user.getEmail(), user.getisStudent())));
        return userDTOs;
    }

    public void DeleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean IsStudentOrNot(String email) {
        return email.contains("@atec.pt");
    }
}

