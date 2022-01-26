package vn.cmc.du21.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmc.du21.userservice.persistence.internal.entity.Role;
import vn.cmc.du21.userservice.persistence.internal.entity.User;
import vn.cmc.du21.userservice.persistence.internal.repository.RoleRepository;
import vn.cmc.du21.userservice.persistence.internal.repository.UserRepository;
import vn.cmc.du21.userservice.presentation.external.mapper.UserMapper;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    public User addUser(User user)
    {
        Role role = roleRepository.findByNameRole("User").orElse(null);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }
public User updateUser(User user) throws Throwable {
    if(!userRepository.existsById(user.getUserId()))
    {
        throw new RuntimeException("userId doesn't existed !!!");
    }
    if(
            !userRepository.findByEmail(user.getEmail()).isEmpty()
                    || !userRepository.findByCellphone(user.getCellphone()).isEmpty()
    )
    {
        if(userRepository.findByEmail(user.getEmail()).get(0).getEmail().equals(user.getEmail())
                && userRepository.findByEmail(user.getEmail()).get(0).getUserId() != user.getUserId())
        {
            throw new RuntimeException("email existed !!!");
        }

        if(userRepository.findByCellphone(user.getCellphone()).get(0).getCellphone().equals(user.getCellphone())
                && userRepository.findByCellphone(user.getCellphone()).get(0).getUserId() != user.getUserId())
        {
            throw new RuntimeException("cellphone existed !!!");
        }
    }
    return userRepository.findById(user.getUserId()).map(u -> {
                u = user;
                userRepository.save(u);
                return u;
                            }
                    ).orElseThrow(()->{
                        throw new RuntimeException("Cannot update user");
                    });

}

    public void deleteById(Long id) throws Throwable
    {
    if(!userRepository.existsById(id))
    {
    throw new RuntimeException("user doesn't exists !!!");
    }else
    {
    userRepository.deleteById(id);
    }
    }
}
