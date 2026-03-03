package atec.beatec.Entities;
import jakarta.persistence.*;


@Entity
@Table (name="Connection")
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // FK → users.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_connection_user")
    )
    private User user;

    // FK → enterprises.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "enterprise_id",
            foreignKey = @ForeignKey(name = "fk_connection_enterprise")
    )
    private Enterprise enterprise;

    @Column(nullable = false)
    private boolean IsIntershipNoJob;

    @Column(nullable = false)
    private String Classname;
    protected Connection() {
        this.user = null;
        this.enterprise = null;
        this.IsIntershipNoJob = false;
        this.Classname = null;
    }


    public Connection(
            User user,
            Enterprise enterprise,
            boolean isinternshipNoJob,
            String classname
    ) {
        this.user = user;
        this.enterprise = enterprise;
        this.IsIntershipNoJob = isinternshipNoJob;
        this.Classname = classname;
    }

    public Connection(
            long id,
            User user,
            Enterprise enterprise,
            boolean isinternshipNoJob,
            String classname
    ) {
        this.id = id;
        this.user = user;
        this.enterprise = enterprise;
        this.IsIntershipNoJob = isinternshipNoJob;
        this.Classname = classname;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }
   public User getUser() {
        return user;
   }

   public Long getId() {
        return id;
    }

    public boolean getIsIntershipNoJob() {
        return IsIntershipNoJob;
    }
    public String getClassname() {
        return Classname;
    }
}

