package atec.beatec.Entities;

public class UserDTO {
    private final Long id;
    private final String name;
    private final int level;
    private final String email;
    private final boolean isStudent;

    public UserDTO(Long id, String name, int level,String email, boolean isStudent) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.email = email;
        this.isStudent = isStudent;
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
    /**
     gets user email
     */
    public String getEmail() {return email;}
    /**
     gets user isStudent value(that dictates if the user is a student or not )
     */
    public boolean isStudent() {return isStudent;}
}
