

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
     * Updates a User details with the given name.
     *
     * @param name The name of the user to update.
     * @param id The id of user to update.
     * @param level The level of the user to update.
     * @return The updated User entity.
     */

    UserDTO updateUser(Long id, String name, int level);

    /**
     * Creates a new User with the given name.
     *
     * @param name The name of the user to create.
     *
     * @param level The level of user to update.
     * @param password The password of the user to update.
     * @return The created User entity.
     */

    UserDTO createUser(String name, int level, String password);

    /**
     * Retrieves a User by its unique ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The User with the given ID, or null if not found.
     */
    UserDTO getById(Long id);

    /**
     * gets a List or a Single user with a given name.
     * @param name The name of the user(s) to find.
     * @return User List or one User entity.
     */
    List<UserDTO> getAllByName(String name);
    /**
     * gets a Single user with a given name.
     * @param name The name of the user to find.
     * @return given User entity with correspondent name.
     */
    UserDTO getByName(String name);
    /**
     * Confirms if User password is correct.
     * @param name The name of the user.
     * @param password password of user
     * @return True if sucess false if failure.
     */

    boolean ConfirmPassword(String name,String password );
}






































/*
 *     /**
 * Retrieves all users with the given name.
 *
 * @param name The name to search for.
 * @return A list of Users with the specified name.
 */