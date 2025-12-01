package edu.gisma.gh1043541.healthcaresystem.repository;

import edu.gisma.gh1043541.healthcaresystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
