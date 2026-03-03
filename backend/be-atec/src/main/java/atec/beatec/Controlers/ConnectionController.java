package atec.beatec.Controlers;

import atec.beatec.Entities.Connection;
import atec.beatec.Entities.ConnectionDTO;
import atec.beatec.Entities.Enterprise;
import atec.beatec.Entities.User;
import atec.beatec.Services.ConnectionService;
import atec.beatec.Services.IConnectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/connections")
public class ConnectionController {
    private final IConnectionService connectionService;

    public ConnectionController(IConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping
    public ResponseEntity<?> insertConnection(@RequestParam long userid,@RequestParam long enterpriseid,@RequestParam Boolean isInternshipNoJob,@RequestParam String classname){
        ConnectionDTO connectionDTO = connectionService.CreateConnection(
                userid, enterpriseid, isInternshipNoJob, classname);
        return ResponseEntity.status(HttpStatus.CREATED).body(connectionDTO);


    }
    @GetMapping("/{id}")
    public ResponseEntity<?> GetConectionbyid(@PathVariable Long id){
        ConnectionDTO connectionDTO = connectionService.getConnectionById(id);
        return ResponseEntity.ok(connectionDTO);

    }
    @GetMapping
    public ResponseEntity<?> GetAllConnections(@RequestParam(defaultValue = "10") int pageSize,@RequestParam(defaultValue = "0") int pageNumber){

        List<ConnectionDTO> consDTO = connectionService.getAllConnections(pageSize, pageNumber);

        if (consDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(consDTO);


    }

    @GetMapping("byEnterprise/{id}")
    public ResponseEntity<?> GetConectionbyEnterprise(@PathVariable Long id,@RequestParam(defaultValue = "10") int pageSize,@RequestParam(defaultValue = "0") int pageNumber){

        List<ConnectionDTO> consDTO = connectionService.getConnectionsByEnterprise(
                id, pageSize, pageNumber);

        if (consDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(consDTO);
    }
    @GetMapping("byUser/{id}")
    public ResponseEntity<?> GetConectionbyUser(@PathVariable Long id,@RequestParam(defaultValue = "10") int pageSize,@RequestParam(defaultValue = "0") int pageNumber){
        List<ConnectionDTO> consDTO = connectionService.getConnectionsByUser(
                id, pageSize, pageNumber);

        if (consDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(consDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateConection(@PathVariable long id,@RequestParam long userid,@RequestParam long enterpriseid,@RequestParam Boolean isInternshipNoJob,@RequestParam String classname){
        ConnectionDTO updatedConnectionDTO = connectionService.UpdateConnection(
                id, userid, enterpriseid, isInternshipNoJob, classname);
        return ResponseEntity.ok(updatedConnectionDTO);
    }
    @DeleteMapping
    public ResponseEntity<?> DeleteConection(@RequestParam Long id){
        connectionService.DeleteConnection(id);
        return ResponseEntity.noContent().build();
    }
}
