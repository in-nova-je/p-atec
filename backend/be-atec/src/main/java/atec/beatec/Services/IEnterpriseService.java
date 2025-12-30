package atec.beatec.Services;

import atec.beatec.Entities.Enterprise;

import java.util.List;

public interface IEnterpriseService {
    /**
     * Updates a Enterprise details with the given name.
     *
     * @param name The name of the Enterprise to update.
     * @param id The id of Enterprise to update.
     * @param FieldsOfInterest The level of the Enterprise to update.
     * @param Websitelink The level of the Enterprise to update.
     * @return The updated Enterprise entity.
     */

    Enterprise updateEnterprise(Long id, String name,String Description, List<String>FieldsOfInterest, String Websitelink);

    /**
     * Creates a Enterprise details with the given name.
     *
     * @param name The name of the Enterprise to update.
     * @param FieldsOfInterest The level of the Enterprise to update.
     * @param Websitelink The level of the Enterprise to update.
     * @return The Created Enterprise entity.
     */

    public Enterprise createEnterprise( String name,String Description,List<String> FieldsOfInterest,String Websitelink);
    /**
     * Retrieves a Enterprise by its unique ID.
     *
     * @param id The ID of the Enterprise to retrieve.
     * @return The Enterprise with the given ID, or null if not found.
     */
    Enterprise getEnterpriseById(Long id);


    /**
     * gets a Single Enterprise with a given name.
     * @param name The name of the enterprise to find.
     * @return given Enterprise entity with correspondent name.
     */
    Enterprise getEnterpriseByName(String name);

    /**
     * list all enterprises in page with a given dimension
     * @param pageSize
     * @param pageNumber
     * @return
     */

    List<Enterprise> ListAllEnterprises(int pageSize, int pageNumber);

    /**
     * deletes a Enterprise with a given id
     * @param id
     */
    void DeleteEnterprise(Long id);
}
