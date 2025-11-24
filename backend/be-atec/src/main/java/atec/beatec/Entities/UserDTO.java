package atec.beatec.Entities;

public class UserDTO {
    private final Long id;
    private final String name;
    private final int level;

    public UserDTO(Long id, String name, int level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    // --------------------
    // Getters
    // --------------------
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
}
