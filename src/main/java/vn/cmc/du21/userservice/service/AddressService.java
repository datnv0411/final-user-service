package vn.cmc.du21.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.cmc.du21.userservice.persistence.internal.entity.Address;
import vn.cmc.du21.userservice.persistence.internal.repository.AddressRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserService userService;

    @Transactional
    public Address addAddress(Address address, long userId)
    {

        address.setUser(userService.findByUserId(userId));
        List<Address> addressList = addressRepository.findByUserId(userId);
        if(addressList.isEmpty()) address.setDefault(true);
        return addressRepository.save(address);
    }

    @Transactional
    public void deleteById(Long id) throws Throwable{
        if(!addressRepository.existsById(id))
        {
            throw new RuntimeException("address doesn't exists !!!");
        }else
        {
            addressRepository.deleteById(id);
        }
    }

    @Transactional
    public Address getAddressByAddressId(long userId, long addressId) {
        return addressRepository.findByAddressIdAndUserId(userId, addressId).orElse(null);
    }

    @Transactional
    public Page<Address> getAllAddress(long userId, int page, int size, String sort)
    {
        List<Address> addressList = addressRepository.findByUserId(userId);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), addressList.size());
        return new PageImpl<>(addressList.subList(start, end), pageable, addressList.size());
    }

}
