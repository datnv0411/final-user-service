package vn.cmc.du21.userservice.persistence.internal.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;
    private String nameRole;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public Role(String nameRole, Set<User> users) {
        this.nameRole = nameRole;
        this.users = users;
    }

    public Role(long roleId, String nameRole, Set<User> users) {
        this.roleId = roleId;
        this.nameRole = nameRole;
        this.users = users;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }
}
