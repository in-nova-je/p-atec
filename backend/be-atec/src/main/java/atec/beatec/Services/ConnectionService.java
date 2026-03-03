package atec.beatec.Services;

import atec.beatec.Entities.*;
import atec.beatec.Exceptions.ConnectionNotFoundException;
import atec.beatec.Repositories.ConnectionRepository;
import atec.beatec.Repositories.EnterpriseRepository;
import atec.beatec.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ConnectionService implements IConnectionService {

    private ConnectionRepository connectionRepository;
    private EnterpriseService enterpriseService;
    private UserService userService;
    private UserRepository userRepository;

    public ConnectionService(ConnectionRepository connectionRepository , UserService userService , EnterpriseService enterpriseService,UserRepository userRepository) {
        this.connectionRepository = connectionRepository;
        this.enterpriseService=enterpriseService;
        this.userService=userService;
        this.userRepository=userRepository;
    }

    @Transactional
    public ConnectionDTO CreateConnection( long userid, long enterpriseid, Boolean isInternshipNoJob, String classname)
    {

        validateConnectionInput(isInternshipNoJob, classname);

        // Buscar user e enterprise (já lançam exceções se não existirem)
        UserDTO userFind=userService.getUserById(userid);
        User user = userRepository.getReferenceById(userid);
        Enterprise enterprise = enterpriseService.getEnterpriseById(enterpriseid);

        // Criar e guardar connection
        Connection connection = new Connection(user, enterprise, isInternshipNoJob, classname);
        Connection saved =connectionRepository.save(connection);

        return mapToDTO(saved);
    }

    @Transactional
    public ConnectionDTO UpdateConnection(long id,long userid, long enterpriseid, Boolean isInternshipNoJob, String classname){
        Connection existingConnection = connectionRepository.findById(id)
                .orElseThrow(() -> new ConnectionNotFoundException(id));

        // Validações
        validateConnectionInput(isInternshipNoJob, classname);
        UserDTO userFind=userService.getUserById(userid);
        User user = userRepository.getReferenceById(userid);
        System.out.println("user id:"+userid +"actual user id obtain for some reason:" +user.getId());

        Enterprise enterprise =enterpriseService.getEnterpriseById(enterpriseid); // already throws exception
        Connection con = new Connection(id,user, enterprise, isInternshipNoJob, classname);
        Connection updated = connectionRepository.save(con);


        return mapToDTO(updated);

    }
    public List<ConnectionDTO> getAllConnections(int pageSize,int pageNumber){
        if (pageSize <= 0 || pageNumber < 0) {
            throw new IllegalArgumentException("Invalid page size or page number");
        }
        List<UserDTO> userDTOs=new ArrayList<>();
        Page<Connection> page = connectionRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<Connection> cons=page.getContent();
        List<ConnectionDTO> conDTOs=new ArrayList<>();
        cons.forEach(con->conDTOs.add(new ConnectionDTO(con.getId(),con.getUser().getId(),con.getEnterprise().getId(),con.getIsIntershipNoJob(),con.getClassname())));
        return conDTOs;

    }
    public List<ConnectionDTO> getConnectionsByUser(Long userId, int pageSize, int pageNumber) {
        if (pageSize <= 0 || pageNumber < 0) {
            throw new IllegalArgumentException("Invalid page size or page number");
        }

        // Verificar se user existe
        userService.getUserById(userId);

        // Assumindo que tens este método no repository
        Page<Connection> page = connectionRepository.findByUserId(userId, PageRequest.of(pageNumber, pageSize));
        List<Connection> cons=page.getContent();
        List<ConnectionDTO> conDTOs=new ArrayList<>();
        cons.forEach(con->conDTOs.add(new ConnectionDTO(con.getId(),con.getUser().getId(),con.getEnterprise().getId(),con.getIsIntershipNoJob(),con.getClassname())));
        return conDTOs;
    }

    public List<ConnectionDTO> getConnectionsByEnterprise(Long enterpriseId, int pageSize, int pageNumber) {
        if (pageSize <= 0 || pageNumber < 0) {
            throw new IllegalArgumentException("Invalid page size or page number");
        }

        // Verificar se enterprise existe
        enterpriseService.getEnterpriseById(enterpriseId);

        // Assumindo que tens este método no repository
        Page<Connection> page = connectionRepository.findByEnterpriseId(enterpriseId,
                PageRequest.of(pageNumber, pageSize));
        List<Connection> cons=page.getContent();
        List<ConnectionDTO> conDTOs=new ArrayList<>();
        cons.forEach(con->conDTOs.add(new ConnectionDTO(con.getId(),con.getUser().getId(),con.getEnterprise().getId(),con.getIsIntershipNoJob(),con.getClassname())));
        return conDTOs;
    }
    @Transactional
    public void DeleteConnection(Long id){
        if (!connectionRepository.existsById(id)) {
            throw new ConnectionNotFoundException(id);
        }
        connectionRepository.deleteById(id);

    }
    public ConnectionDTO getConnectionById(Long id){
        Connection con= connectionRepository.findById(id)
                .orElseThrow(() -> new ConnectionNotFoundException(id));
        return mapToDTO(con);

    }

    private void validateConnectionInput(Boolean isInternshipNoJob, String classname) {
        if (isInternshipNoJob == null) {
            throw new IllegalArgumentException("Internship type cannot be null");
        }
        if (classname == null || classname.trim().isEmpty()) {
            throw new IllegalArgumentException("Classname cannot be empty");
        }
    }
    private ConnectionDTO mapToDTO(Connection connection) {
        return new ConnectionDTO(
                connection.getId(),
                connection.getUser().getId(),
                connection.getEnterprise().getId(),
                connection.getIsIntershipNoJob(),
                connection.getClassname()
        );
    }

}
