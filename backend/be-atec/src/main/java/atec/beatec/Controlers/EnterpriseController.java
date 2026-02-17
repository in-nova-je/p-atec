package atec.beatec.Controlers;

import atec.beatec.Entities.Enterprise;
import atec.beatec.Services.IEnterpriseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Enterprise")
public class EnterpriseController {

    private final IEnterpriseService EnterpriseService;

    public EnterpriseController(IEnterpriseService EnterpriseService) {
        this.EnterpriseService = EnterpriseService;
    }


    @PostMapping
    public ResponseEntity<?> createEnterprise( @RequestParam String name,@RequestParam String Description,@RequestParam String Websitelink) {

        /*if (EnterpriseService.getEnterpriseByName(name) != null) {
            return new ResponseEntity<>("Enterprise already exists", HttpStatus.CONFLICT);
        }
        Enterprise enterprise=EnterpriseService.createEnterprise(name,Description,Websitelink);
        return new ResponseEntity<>(enterprise, HttpStatus.CREATED);
        */
         // Verificar se já existe
        if (EnterpriseService.existsByName(name)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Enterprise enterprise = EnterpriseService.createEnterprise(
                name, Description, Websitelink);
        return ResponseEntity.status(HttpStatus.CREATED).body(enterprise);
    }

    @GetMapping("/{id}")  //done
    public ResponseEntity<?> getById(@PathVariable Long id) {
        /*
        try {
            Enterprise Enterprise = EnterpriseService.getEnterpriseById(id);
            return ResponseEntity.ok(Enterprise);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        */
        Enterprise enterprise = EnterpriseService.getEnterpriseById(id);
        return ResponseEntity.ok(enterprise);
    }

    /**
     * Get all users with a given name.
     *
     * @param name Name to search for
     * @return List of users with the specified name
     */
    @GetMapping("/by-name")
    public ResponseEntity<?> getAllByName(@RequestParam String name) {
        /*
        var Enterprise = EnterpriseService.getEnterpriseByName(name); // dar handle de se correr mal
        if (Enterprise == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Enterprise);
        */
        Enterprise enterprise = EnterpriseService.getEnterpriseByName(name);
        return ResponseEntity.ok(enterprise);
    }

    /**
     * atualiza todos os fields
     * @param id
     * @param name
     * @param Description
     * @param Websitelink
     * @return
     */
    @PutMapping("/{id}") //
    public ResponseEntity<?> AlterByIdEnterprise(@PathVariable Long id, @RequestParam String name,String Description,@RequestParam String Websitelink) {

        Enterprise updatedEnterprise = EnterpriseService.updateEnterprise(
                id, name, Description, Websitelink);
        return ResponseEntity.ok(updatedEnterprise);
    }

    /**
     * updates name
     * @param id
     * @param name
     * @return
     */
    @PutMapping("name/{id}")
    public ResponseEntity<?> AlterByIdNameEnterprise(@PathVariable Long id, @RequestParam String name) {

        Enterprise enterprise = EnterpriseService.getEnterpriseById(id);
        Enterprise updatedEnterprise = EnterpriseService.updateEnterprise(
                id, name, enterprise.getDescription(), enterprise.getWebsiteLink());
        return ResponseEntity.ok(updatedEnterprise);
    }

    /**
     * updates description
     * @param name
     * @param Description
     * @return
     */
    @PutMapping("description/{name}")
    public ResponseEntity<?> AlterByIdDescription(@PathVariable String name, @RequestParam String Description) {


        Enterprise enterprise = EnterpriseService.getEnterpriseByName(name);
        Enterprise updatedEnterprise = EnterpriseService.updateEnterprise(
                enterprise.getId(), enterprise.getName(), Description,
                 enterprise.getWebsiteLink());
        return ResponseEntity.ok(updatedEnterprise);
    }

    /**
     * updates name
     * @param name
     * @param WebsiteLink
     * @return
     */
    @PutMapping("websitelink/{name}")
    public ResponseEntity<?> AlterByIdWebsiteLink(@PathVariable String name, @RequestParam String WebsiteLink) {

        Enterprise enterprise = EnterpriseService.getEnterpriseByName(name);
        Enterprise updatedEnterprise = EnterpriseService.updateEnterprise(
                enterprise.getId(), enterprise.getName(), enterprise.getDescription(),
                 WebsiteLink);
        return ResponseEntity.ok(updatedEnterprise);
    }



    /**
     *
     * @param pageSize size of page
     * @param pageNumber number of users
     * @return all given users in a given page
     */
    @GetMapping
    public ResponseEntity<?> getAllEnterprises(@RequestParam(defaultValue = "10") int pageSize,@RequestParam(defaultValue = "0") int pageNumber) {
        List<Enterprise> allEnterprises= EnterpriseService.ListAllEnterprises(pageSize,pageNumber);
        if(allEnterprises.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allEnterprises);
    }

    /**
     *
     * @param id
     * @return sucess mesage "delete" or not found response if the user doesnt exist
     */
    @DeleteMapping
    public ResponseEntity<?> deleteByIdEnterprise(@RequestParam Long id) {
        /*try {
            var Enterprise = EnterpriseService.getEnterpriseById(id);
            EnterpriseService.DeleteEnterprise(id);
            return ResponseEntity.ok("deleted");
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }*/
        EnterpriseService.DeleteEnterprise(id);
        return ResponseEntity.noContent().build();
    }


}
