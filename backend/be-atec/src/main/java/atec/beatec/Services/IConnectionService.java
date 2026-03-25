package atec.beatec.Services;

import atec.beatec.Entities.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IConnectionService {
    ConnectionDTO CreateConnection(long userid, long enterpriseid, Boolean isinternshipNoJob, String classname);

    public ConnectionDTO UpdateConnection(long id, Boolean isInternshipNoJob, String classname);

    List<ConnectionDTO> getAllConnections(int pageSize, int pageNumber);

    void DeleteConnection(Long id);

    ConnectionDTO getConnectionById(Long id);

    List<ConnectionDTO> getConnectionsByEnterprise(Long enterpriseId, int pageSize, int pageNumber);

    public List<ConnectionDTO> getConnectionsByUser(Long userId, int pageSize, int pageNumber);


}




