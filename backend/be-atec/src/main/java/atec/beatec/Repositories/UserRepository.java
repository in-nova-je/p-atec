package atec.beatec.Repositories;

import atec.beatec.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByName(String name);  // returns list (if duplicates exist)
    User findByName(String name);
    Optional<User> findByEmail(String email);
}
