package atec.beatec.Entities;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name="Enterprise")
public class Enterprise {
    @Id //key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private final String name;

    @Column(nullable = false)
    private final String description;



    @Column(nullable = false)
    @ElementCollection
    private final List<String> FieldsOfInterest; // store hashed password

    @Column(nullable = false)
    private final String WebsiteLink;


    /**
     * No-arg constructor required by JPA.
     * Private to prevent accidental use.
     */
    protected Enterprise() {
        this.name = null;
        this.description = null;
        this.FieldsOfInterest = null;
        this.WebsiteLink = null;
    }

    /**
     * Constructor for creating a new User.
     *
     * @param name     The user's name
     * @param description The Enterprise's description
     * @param FieldsOfInterest The Enterprise's Fields Of Interest
     *  @param WebsiteLink The Enterprise's Website Link
     */

    public Enterprise(String name, String description,List<String> FieldsOfInterest, String WebsiteLink) {
        this.name = name;
        this.description =description;
        this.FieldsOfInterest = FieldsOfInterest;
        this.WebsiteLink =  WebsiteLink;
    }

    // --------------------
    // Getters
    // --------------------
    /**
     gets Enterpise id
     */
    public Long getId() {
        return id;
    }
    /**
     gets Enterpise name
     */
    public String getName(){
        return name;
    }
    /**
     gets Enterpise Description
     */
    public  String getDescription(){
        return description;
    }
    /**
     gets Enterpise  fields of interest
     */
    public List<String> getFieldsOfInterest() {
        return FieldsOfInterest;
    }
    /**
     gets Enterpise website link
     */
    public String getWebsiteLink() {
        return WebsiteLink;
    }

}
