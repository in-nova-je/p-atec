package atec.beatec.Repositories;

import atec.beatec.Entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
    List<Enterprise> findAllByName(String name);  // returns list (if duplicates exist)
    Enterprise findByName(String name);
}
