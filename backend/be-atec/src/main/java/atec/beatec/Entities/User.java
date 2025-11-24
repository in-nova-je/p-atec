package atec.beatec.Entities;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id //key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private final String name;

    @Column(nullable = false)
    private final int level;


    @SuppressWarnings("unused")
    @Column(nullable = false)
    private final String password; // store hashed password


    /**
     * No-arg constructor required by JPA.
     * Private to prevent accidental use.
     */
    protected User() {
        this.name = null;
        this.password = null;
        this.level = 0;
    }

    /**
     * Constructor for creating a new User.
     *
     * @param name     The user's name
     * @param password The user's password (hashed)
     */
    public User(String name, int level, String password) {
        this.name = name;
        this.level = level;
        this.password = password;
    }

    public User(long id,String name, int level, String password) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.password = password;
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
    public String getPassword() {return password;}















    /* *
     *  @Column(length = 50, nullable = false)
     */
}
