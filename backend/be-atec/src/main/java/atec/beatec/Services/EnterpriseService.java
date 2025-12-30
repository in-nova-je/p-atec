package atec.beatec.Services;

import atec.beatec.Entities.Enterprise;
import atec.beatec.Entities.User;
import atec.beatec.Entities.UserDTO;
import atec.beatec.Repositories.EnterpriseRepository;
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

    public Enterprise updateEnterprise(Long id, String name, String Description,List<String> FieldsOfInterest, String Websitelink){

        Enterprise enterprise= new  Enterprise( id,name, Description,FieldsOfInterest,Websitelink);
        enterpriseRepository.save(enterprise);
        return null;
    }


    public Enterprise createEnterprise( String name,String Description,List<String> FieldsOfInterest,String Websitelink){
        Enterprise enterprise=new Enterprise(  name,Description, FieldsOfInterest,Websitelink);
        enterpriseRepository.save(enterprise);
        return enterprise;
    }


    public Enterprise getEnterpriseById(Long id){
        return enterpriseRepository.findById(id).orElse(null);
    }

    public Enterprise getEnterpriseByName(String name){
        return enterpriseRepository.findByName(name);
    }
    @Override
    public List<Enterprise> ListAllEnterprises(int pageSize,int pageNumber){
        Page<Enterprise> page = enterpriseRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return page.getContent();
    }

    public void DeleteEnterprise(Long id){
        enterpriseRepository.deleteById(id);

    }
}

