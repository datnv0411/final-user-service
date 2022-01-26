package vn.cmc.du21.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmc.du21.userservice.persistence.internal.entity.Role;
import vn.cmc.du21.userservice.persistence.internal.entity.User;
import vn.cmc.du21.userservice.persistence.internal.repository.RoleRepository;
import vn.cmc.du21.userservice.persistence.internal.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    public User findByCellphone(String cellphone) {
        return userRepository.findByCellphone(cellphone).orElse(null);
    }

    public User addUser(User user)
    {
        Role role = roleRepository.findByNameRole("User").orElse(null);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
