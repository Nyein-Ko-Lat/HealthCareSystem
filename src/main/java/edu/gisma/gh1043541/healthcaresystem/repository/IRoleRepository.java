package edu.gisma.gh1043541.healthcaresystem.repository;
import edu.gisma.gh1043541.healthcaresystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}