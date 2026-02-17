package atec.beatec.Exceptions;

public class ConnectionNotFoundException extends NotFoundException {
    public ConnectionNotFoundException(Long id) {
        super("Connection not found with id: " + id);
    }
}