package atec.beatec.Repositories;

import atec.beatec.Entities.Connection;
import atec.beatec.Entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {


    Page<Connection> findByEnterpriseId(Long enterpriseId, Pageable pageable);
    Page<Connection> findByUserId(Long userId, Pageable pageable);
}
