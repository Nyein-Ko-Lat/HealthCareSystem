package edu.gisma.gh1043541.healthcaresystem.repository;

import edu.gisma.gh1043541.healthcaresystem.entity.UserRole;
import edu.gisma.gh1043541.healthcaresystem.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}