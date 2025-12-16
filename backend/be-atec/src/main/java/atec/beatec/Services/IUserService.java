

package atec.beatec.Services;
import atec.beatec.Entities.User;
import atec.beatec.Entities.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing User entities.
 * Provides methods for creating and retrieving users.
 */
@Service
public interface IUserService {

    /**
     * Creates a new User with the given name.
     *
     * @param name The name of the user to create.
     * @return The created User entity.
     */

    UserDTO updateUser(Long id, String name, int age);

    UserDTO createUser(String name, int age, String password);

    /**
     * Retrieves a User by its unique ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The User with the given ID, or null if not found.
     */
    UserDTO getById(Long id);
    List<UserDTO> getAllByName(String name);
    UserDTO getByName(String name);
    boolean ConfirmPassword(String name,String password );
}






































/*
 *     /**
 * Retrieves all users with the given name.
 *
 * @param name The name to search for.
 * @return A list of Users with the specified name.
 */