
package atec.beatec.Services;
import atec.beatec.Entities.User;
import atec.beatec.Entities.UserDTO;
import atec.beatec.Exceptions.UserNotFoundException;
import atec.beatec.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public UserDTO createUser(String name, int level, String password,String email,String FieldsOfInterest) {

        validateUserInput(name,email,password);
        //for conditions use a method validator to go allong the solid principles
        User user = new User(name, level,passwordEncoder.encode(password),email,IsStudentOrNot(email),FieldsOfInterest);//hashing
        var saved = userRepository.save(user);
        return new UserDTO(saved.getId(), saved.getName(), saved.getLevel(), saved.getEmail(), saved.getisStudent(),saved.getFieldsOfInterest());
    }
    // Get user by ID
    public UserDTO getUserById(Long id) {
        var result =  userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new UserDTO(result.getId(), result.getName(), result.getLevel(),result.getEmail(), result.getisStudent(), result.getFieldsOfInterest());
    }

    public UserDTO getUserByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
           var user = userRepository.findByName(name);
        if (user == null) {
            throw new UserNotFoundException("User not found with name: " + name);
        }
        return new UserDTO(user.getId(), user.getName(), user.getLevel(), user.getEmail(), user.getisStudent(), user.getFieldsOfInterest());


    }
   public UserDTO getUserByEmail(String email){
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }


       if (email == null || email.trim().isEmpty()) {
           throw new IllegalArgumentException("Email cannot be empty");
       }

       try {
           Optional<User> userOptional = userRepository.findByEmail(email);

           if (userOptional.isEmpty()) {
               throw new UserNotFoundException("User not found with email: " + email);
           }

           User user = userOptional.get();
           return new UserDTO(user.getId(), user.getName(), user.getLevel(),
                   user.getEmail(), user.getisStudent(), user.getFieldsOfInterest());

       } catch (IncorrectResultSizeDataAccessException e) {
           throw new IllegalStateException("Data integrity error: Multiple users found with email: " + email);
       }
/*
            var user = userRepository.findByEmail(email);


        if (user == null) {
           throw new UserNotFoundException("User not found with name: " + email);
       }
       return new UserDTO(user.getId(), user.getName(), user.getLevel(), user.getEmail(), user.getisStudent(), user.getFieldsOfInterest());
*/

   }

    //updates user fields
    public UserDTO updateUser(Long id, String name, int level,String email,String FieldsOfInterest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        validateUserInput(name, email, null);

        User updatedUser= new User(id, name, level,null,email,IsStudentOrNot(email),FieldsOfInterest);
        User saved=userRepository.save(updatedUser);
        return new UserDTO(saved.getId(),saved.getName(),saved.getLevel(),saved.getEmail(),saved.getisStudent(), saved.getFieldsOfInterest());
    }
    //confirms that user password is correct
    public boolean ConfirmPassword(String name, String password) {//no futuro vai ser hash da password
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new UserNotFoundException("User not found with name: " + name);
        }
        return passwordEncoder.matches(password, user.getPassword());
    }
    @Override
    public List<UserDTO> ListAllUsers(int pageSize,int pageNumber) {
        if (pageSize <= 0 || pageNumber < 0) {
            throw new IllegalArgumentException("Invalid page size or page number");
        }
        Page<User> page = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<User> users = page.getContent();
        List<UserDTO> userDTOs=new ArrayList<>();
        users.forEach(user -> userDTOs.add(new UserDTO(user.getId(), user.getName(), user.getLevel(), user.getEmail(), user.getisStudent(), user.getFieldsOfInterest())));
        return userDTOs;
    }

    public void DeleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }


    private void validateUserInput(String name, String email, String password) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (password != null && password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }


    public boolean IsStudentOrNot(String email) {
        return email.contains("@atec.pt");
    }
}

