package atec.beatec.Entities;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id //key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  String name;

    @Column(nullable = false)
    private  int level;

    @Column(nullable = false , columnDefinition = "varchar(255) default ''")//tirar isto depois de apagar users tds
    private  String email;

    @Column(nullable = false, columnDefinition = "boolean default false")//tirar isto depois de apagar users tds
    private boolean isStudent;

    @Column
    private String FieldsOfInterest;

    @Column(name = "profile_picture", columnDefinition = "VARCHAR(1500000)")
    private String ProfilePicture;

    @SuppressWarnings("unused")
    @Column(nullable = false)
    private String password; // store hashed password

    


    /**
     * No-arg constructor required by JPA.
     * Private to prevent accidental use.
     */
    protected User() {
        this.name = null;
        this.password = null;
        this.email = null;
        this.isStudent = false;
        this.level = 0;
        this.FieldsOfInterest=null;
        this.ProfilePicture = null;
    }

    /**
     * Constructor for creating a new User.
     *
     * @param name     The user's name
     * @param password The user's password (hashed)
     */

    public User(String name, int level, String password,String email, boolean isStudent,String fieldsOfInterest,String profilePicture) {
        this.name = name;
        this.level = level;
        this.email = email;
        this.isStudent = isStudent;
        this.password = password;
        this.FieldsOfInterest=fieldsOfInterest;
        this.ProfilePicture=profilePicture;

    }


    /**
     * Constructor for creating a new User.
     *
     * @param id
     * @param name     The user's name
     * @param level the users level
     * @param password   The user's password (hashed)
     */
    public User(long id,String name, int level, String password,String email, boolean isStudent,String fieldsOfInterest,String profilePicture) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.email = email;
        this.isStudent = isStudent;
        this.password = password;
        this.FieldsOfInterest=fieldsOfInterest;
        this.ProfilePicture=profilePicture;

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
     gets user password
     */
    public String getPassword() {return password;}
    /**
     gets user email
     */
    public String getEmail() {return email;}
    /**
     gets user isStudent value(that dictates if the user is a student or not )
     */
    public boolean getisStudent() {return isStudent;}

    public String getFieldsOfInterest() {return FieldsOfInterest;}

    public String getProfilePicture() {return ProfilePicture;}

    public void setName(String name) {this.name=name;}

    public void setLevel(int level) {this.level=level;}

    public void setEmail(String email) {this.email=email;}

    public void setFieldsOfInterest(String fieldsOfInterest) {this.FieldsOfInterest=fieldsOfInterest;}

    public void setProfilePicture(String profilePicture) {this.ProfilePicture=profilePicture;}























}
