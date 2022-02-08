package vn.cmc.du21.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.cmc.du21.userservice.persistence.internal.entity.Address;
import vn.cmc.du21.userservice.persistence.internal.entity.Role;
import vn.cmc.du21.userservice.persistence.internal.entity.User;
import vn.cmc.du21.userservice.persistence.internal.repository.AddressRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Transactional
    public Address addAddress(Address address)
    {
        Role role = roleRepository.findByNameRole("User").orElse(null);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return addressRepository.save(user);
    }

    @Transactional
    public Address updateAddress(Address address) throws Throwable{

        if(!addressRepository.existsById(address.getAddressId()))
        {
            throw new IndexOutOfBoundsException("address doesn't existed !!!");
        }

        Optional<Address> foundUserByEmail = addressRepository.findByEmailMinusItself(user.getEmail(), user.getUserId());
        if(foundUserByEmail.isPresent()) {
            throw new RuntimeException("email existed !!!");
        }

        Optional<User> foundUserByCellphone = addressRepository.findBCellphoneMinusItself(user.getCellphone(), user.getUserId());
        if(foundUserByCellphone.isPresent()) {
            throw new RuntimeException("cellphone existed !!!");
        }

        return addressRepository.findById(user.getUserId())
                .map(u -> {
                    u = user;
                    addressRepository.save(u);
                    return u;
                })
                .orElseThrow(()->{
                    throw new RuntimeException("Cannot update user");
                });

    }

    @Transactional
    public void deleteById(Long id) throws Throwable{
        if(!addressRepository.existsById(id))
        {
            throw new RuntimeException("user doesn't exists !!!");
        }else
        {
            addressRepository.deleteById(id);
        }
    }

    @Transactional
    public Address getAddressById(Long id) throws Throwable{
        return addressRepository.findById(id).orElseThrow(() -> {
                    throw new RuntimeException("not found !!!");
                }
        );
    }

    @Transactional
    public Page<Address> getAllAddress(int page, int size, String sort)
    {
        return addressRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
    }
}
