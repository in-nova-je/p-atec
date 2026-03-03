package atec.beatec.Entities;

public class ConnectionDTO {
    private Long id;
    private Long userId;
    private Long enterpriseId;
    private boolean isInternshipNoJob;
    private String classname;

    public ConnectionDTO(
            Long id,
            Long userId,
            Long enterpriseId,
            boolean isInternshipNoJob,
            String classname
    ) {
        this.id = id;
        this.userId = userId;
        this.enterpriseId = enterpriseId;
        this.isInternshipNoJob = isInternshipNoJob;
        this.classname = classname;
    }
    public Long getUserid() {
        return userId;
    }
    public Long getEnterpriseid() {
        return enterpriseId;
    }

    public Long getId() {
        return id;
    }

    public Boolean getIsIntershipNoJob() {
        return isInternshipNoJob;
    }
    public String getClassname() {
        return classname;
    }
}
