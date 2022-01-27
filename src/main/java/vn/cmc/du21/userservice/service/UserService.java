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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    public User updateUser(User user) throws Throwable {

        if(!userRepository.existsById(user.getUserId()))
        {
            throw new IndexOutOfBoundsException("user doesn't existed !!!");
        }

        Optional<User> foundUserByEmail = userRepository.findByEmailMinusItself(user.getEmail(), user.getUserId());
        if(foundUserByEmail.isPresent()) {
                throw new RuntimeException("email existed !!!");
        }

        Optional<User> foundUserByCellphone = userRepository.findBCellphoneMinusItself(user.getCellphone(), user.getUserId());
        if(foundUserByCellphone.isPresent()) {
                throw new RuntimeException("cellphone existed !!!");
        }

        return userRepository.findById(user.getUserId())
                .map(u -> {
                    u = user;
                    userRepository.save(u);
                    return u;
                })
                .orElseThrow(()->{
                    throw new RuntimeException("Cannot update user");
                });

    }

    public void deleteById(Long id) throws Throwable {
        if(!userRepository.existsById(id))
        {
            throw new RuntimeException("user doesn't exists !!!");
        }else
        {
            userRepository.deleteById(id);
        }
    }

    public User getUserById(Long id) throws Throwable{
        return userRepository.findById(id).orElseThrow(() -> {
                    throw new RuntimeException("not found !!!");
            }
        );
    }

    public void checkEmailOrCellphoneExists(String email, String cellphone) throws Throwable {
        Optional<User> foundUserByEmail = userRepository.findByEmail(email);
        if(foundUserByEmail.isPresent()) {
            throw new RuntimeException("email existed !!!");
        }

        Optional<User> foundUserByCellphone = userRepository.findByCellphone(cellphone);
        if(foundUserByCellphone.isPresent()) {
            throw new RuntimeException("cellphone existed !!!");
        }
    }

    public List<User> getAllUsers(int page, int size, String sort)
    {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by(sort))).stream().collect(Collectors.toList());
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
