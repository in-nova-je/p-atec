

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

    UserDTO updateUser(Long id, String name, int level,String email,String FieldsOfInterest);

    /**
     * Creates a new User with the given name.
     *
     * @param name The name of the user to create.
     *
     * @param level The level of user to update.
     * @param password The password of the user to update.
     * @return The created User entity.
     */

    UserDTO createUser(String name, int level, String password,String email,String FieldsOfInterest);

    /**
     * Retrieves a User by its unique ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The User with the given ID, or null if not found.
     */
    UserDTO getUserById(Long id);


    /**
     * gets a Single user with a given name.
     * @param name The name of the user to find.
     * @return given User entity with correspondent name.
     */
    UserDTO getUserByName(String name);
    /**
     * Confirms if User password is correct.
     * @param name The name of the user.
     * @param password password of user
     * @return True if sucess false if failure.
     */

    boolean ConfirmPassword(String name,String password );


    UserDTO getUserByEmail(String email);
    /**
     * list all Users in page with a given dimension
     * @param pageSize
     * @param pageNumber
     * @return
     */
    List<UserDTO> ListAllUsers(int pageSize,int pageNumber);

    /**
     * deletes a user with a give id
     * @param id
     */
    void DeleteUser(Long id);


    public boolean IsStudentOrNot(String email);
}






































/*
 *     /**
 * Retrieves all users with the given name.
 *
 * @param name The name to search for.
 * @return A list of Users with the specified name.
 */