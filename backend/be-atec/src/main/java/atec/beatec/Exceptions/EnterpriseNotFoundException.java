package atec.beatec.Exceptions;

public class EnterpriseNotFoundException extends NotFoundException {
    public EnterpriseNotFoundException(Long id) {
        super("Enterprise not found with id: " + id);
    }
    public EnterpriseNotFoundException(String Name) {
        super("Enterprise not found: " + Name);
    }
}