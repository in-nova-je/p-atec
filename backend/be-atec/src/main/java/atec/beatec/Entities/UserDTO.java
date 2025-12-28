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
    /**
     gets user id
     */
    public Long getId() {
        return id;
    }
    /**
     gets username
     */
    public String getName() {
        return name;
    }
    /**
     gets user level
     */
    public int getLevel() {
        return level;
    }
}
