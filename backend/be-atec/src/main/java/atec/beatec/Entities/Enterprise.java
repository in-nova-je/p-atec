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
    private  String name;

    @Column(nullable = false)
    private String description;


    // store hashed password

    @Column(nullable = false)
    private String WebsiteLink;


    /**
     * No-arg constructor required by JPA.
     * Private to prevent accidental use.
     */
    protected Enterprise() {
        this.name = null;
        this.description = null;
        this.WebsiteLink = null;
    }

    /**
     * Constructor for creating a new User.
     *
     * @param name     The user's name
     * @param description The Enterprise's description
     *  @param WebsiteLink The Enterprise's Website Link
     */

    public Enterprise(String name, String description, String WebsiteLink) {
        this.name = name;
        this.description =description;
        this.WebsiteLink =  WebsiteLink;
    }

    public Enterprise(Long id,String name, String description,String WebsiteLink) {
        this.id = id;
        this.name = name;
        this.description =description;
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
    /**
     gets Enterpise website link
     */
    public String getWebsiteLink() {
        return WebsiteLink;
    }

    public void setName(String name){this.name = name;}
    public void setDescription(String description){this.description = description;}
    public void setWebsiteLink(String WebsiteLink){this.WebsiteLink = WebsiteLink;}



}
