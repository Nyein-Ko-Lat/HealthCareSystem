package edu.gisma.gh1043541.healthcaresystem.entity;

import jakarta.persistence.*;
import org.springframework.data.domain.Auditable;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_roles")
@IdClass(UserRoleId.class)
public class UserRole extends BaseEntity {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "role_id")
    private Long roleId;


    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }

}

