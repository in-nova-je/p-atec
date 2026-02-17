package atec.beatec.Services;

import atec.beatec.Entities.Enterprise;
import atec.beatec.Entities.User;
import atec.beatec.Entities.UserDTO;
import atec.beatec.Exceptions.EnterpriseNotFoundException;
import atec.beatec.Repositories.EnterpriseRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EnterpriseService implements IEnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    EnterpriseService(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }


    @Transactional
    public Enterprise updateEnterprise(Long id, String name, String Description, String Websitelink){

        Enterprise existingEnterprise = enterpriseRepository.findById(id)
                .orElseThrow(() -> new EnterpriseNotFoundException(id));
        validateEnterpriseInput(name,Description, Websitelink);
        Enterprise enterprise= new  Enterprise( id,name, Description,Websitelink);
        return enterpriseRepository.save(enterprise);

    }


    public Enterprise createEnterprise( String name,String Description,String Websitelink){
        Enterprise enterprise=new Enterprise( name,Description,Websitelink);
        enterpriseRepository.save(enterprise);
        return enterprise;
    }


    public Enterprise getEnterpriseById(Long id){
        return enterpriseRepository.findById(id).orElseThrow(() -> new EnterpriseNotFoundException(id));
    }

    public Enterprise getEnterpriseByName(String name){

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        Enterprise enterprise = enterpriseRepository.findByName(name);
        if (enterprise == null) {
            throw new EnterpriseNotFoundException("Enterprise not found with name: " + name);
        }
        return enterprise;
    }

    public List<Enterprise> ListAllEnterprises(int pageSize,int pageNumber){
        /*
        Page<Enterprise> page = enterpriseRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return page.getContent();

         */
        if (pageSize <= 0 || pageNumber < 0) {
            throw new IllegalArgumentException("Invalid page size or page number");
        }

        Page<Enterprise> page = enterpriseRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return page.getContent();

    }
    @Transactional
    public void DeleteEnterprise(Long id){
        if (!enterpriseRepository.existsById(id)) {
            throw new EnterpriseNotFoundException(id);
        }
        enterpriseRepository.deleteById(id);

    }

    public boolean existsByName(String name) {
        return enterpriseRepository.findByName(name) != null;
    }

    private void validateEnterpriseInput(String name, String description, String websiteLink) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (websiteLink == null || websiteLink.trim().isEmpty()) {
            throw new IllegalArgumentException("Website link cannot be empty");
        }
    }
}

