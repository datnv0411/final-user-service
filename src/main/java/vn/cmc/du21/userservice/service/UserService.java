package vn.cmc.du21.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.cmc.du21.userservice.persistence.internal.entity.Role;
import vn.cmc.du21.userservice.persistence.internal.entity.User;
import vn.cmc.du21.userservice.persistence.internal.repository.RoleRepository;
import vn.cmc.du21.userservice.persistence.internal.repository.UserRepository;
import vn.cmc.du21.userservice.presentation.external.mapper.UserMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<User> getAllUsers(int page, int size, String sort)
    {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by(sort))).stream().collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        try
        {
            return userRepository.findById(id).orElse(null);
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public int totalPage (int page, int size, String sort){
        Page<User> pageTT = userRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
        return pageTT.getTotalPages();
    }
    public Long totalRecord (int page, int size, String sort){
        Page<User> pageTT = userRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
        return pageTT.getTotalElements();
    }

}
