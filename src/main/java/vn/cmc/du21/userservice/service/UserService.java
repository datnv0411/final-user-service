package vn.cmc.du21.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmc.du21.userservice.persistence.internal.entity.Role;
import vn.cmc.du21.userservice.persistence.internal.entity.User;
import vn.cmc.du21.userservice.persistence.internal.repository.RoleRepository;
import vn.cmc.du21.userservice.persistence.internal.repository.UserRepository;
import vn.cmc.du21.userservice.presentation.external.response.UserResponse;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public User findByCellphone(String cellphone) throws Throwable{

        return userRepository.findByCellphone(cellphone).orElse(null);
    }

    @Transactional
    public  User findByUserId(long userId) throws Throwable{

        return userRepository.findByUserId(userId).orElseThrow(
                ()->{
                    throw new RuntimeException("User does not exist !!!");
                }
        );
    }

    @Transactional
    public User addUser(User user) throws Throwable{
        Role role = roleRepository.findByNameRole("User").orElseThrow(
                ()->{
                    throw new RuntimeException("Error add role for user, Role does not exist !!!");
                }
        );
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) throws Throwable{

        if(!userRepository.existsById(user.getUserId()))
        {
            throw new IndexOutOfBoundsException("User doesn't exist !!!");
        }

        Optional<User> foundUserByEmail = userRepository.findByEmailMinusItself(user.getEmail(), user.getUserId());
        if(foundUserByEmail.isPresent()) {
            throw new RuntimeException("Email existed !!!");
        }

        return userRepository.findById(user.getUserId())
                .map(u -> {
                    u.setFullName(user.getFullName());
                    u.setDob(user.getDob());
                    u.setGender(user.getGender());
                    u.setEmail(user.getEmail());
                    userRepository.save(u);
                    return u;
                })
                .orElseThrow(()->{
                    throw new RuntimeException("Cannot update user");
                });
    }

    @Transactional
    public User getUserById(Long id) throws Throwable{

        return userRepository.findById(id).orElseThrow(() -> {
                    throw new RuntimeException("Not found !!!");
                }
        );
    }

    public User checkCellphoneExistsInSessionTable(String cellphone) throws Throwable {

        if(findByCellphone(cellphone) == null)
        {
            User user = new User();
            user.setCellphone(cellphone);
            // add new user with role default 'User'
            return addUser(user);
        }
        else
        {
            // get user
            return findByCellphone(cellphone);
        }
    }

    public void checkUserLogin(UserResponse userLogin, Long userId) {
        if(userLogin.getUserId() != userId)
        {
            throw new RuntimeException("You can not access");
        }
    }
}