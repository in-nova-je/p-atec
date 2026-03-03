package atec.beatec.Exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
    public UserNotFoundException(String Name) {
        super("User not found with name: " + Name);
    }
}