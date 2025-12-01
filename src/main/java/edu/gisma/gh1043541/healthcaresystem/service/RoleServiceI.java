
package edu.gisma.gh1043541.healthcaresystem.service;

import edu.gisma.gh1043541.healthcaresystem.entity.Role;
import edu.gisma.gh1043541.healthcaresystem.repository.IRoleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleServiceI implements IBaseService<Role, Long> {
    private final IRoleRepository roleRepository;

    @Override
    public void delete(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public void delete(Long id, Long UpdatedBy) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public RoleServiceI(IRoleRepository IroleRepository) {
        this.roleRepository = IroleRepository;
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
