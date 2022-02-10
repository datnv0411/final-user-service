package vn.cmc.du21.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.cmc.du21.userservice.persistence.internal.entity.Address;
import vn.cmc.du21.userservice.persistence.internal.entity.User;
import vn.cmc.du21.userservice.persistence.internal.repository.AddressRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserService userService;

    @Transactional
    public Address addAddress(Address address, long userId)
    {
        User userLogin = userService.findByUserId(userId);
        address.setUser(userLogin);
        List<Address> addressList = addressRepository.findByUserId(userId);

        if(addressList.isEmpty()) address.setDefault(true);
        else
        {
            if(address.isDefault())
            {
                for(Address item : addressList)
                {
                    if(item.isDefault())
                    {
                        item.setDefault(false);
                        addressRepository.save(item);
                    }
                }
            }
        }
        return addressRepository.save(address);
    }

    @Transactional
    public Address updateAddress(Address address, long userId){
        Optional<Address> foundAddress = addressRepository.findByAddressIdAndUserId(userId, address.getAddressId());

        if(foundAddress.isPresent())
        {
            foundAddress.get().setDefault(address.isDefault());
            foundAddress.get().setTypeAddress(address.getTypeAddress());
            foundAddress.get().setFullName(address.getFullName());
            foundAddress.get().setCellphone(address.getCellphone());
            foundAddress.get().setProvince(address.getProvince());
            foundAddress.get().setDistrict(address.getDistrict());
            foundAddress.get().setTown(address.getTown());
            foundAddress.get().setSpecificAddress(address.getSpecificAddress());

            if(foundAddress.get().isDefault())
            {
                List<Address> addressList = addressRepository.findByUserId(userId);
                for(Address item : addressList)
                {
                    if(item.getAddressId() != foundAddress.get().getAddressId()
                        && item.isDefault())
                    {
                        item.setDefault(false);
                        addressRepository.save(item);
                    }
                }
            }
            addressRepository.save(foundAddress.get());
            return foundAddress.get();
        }

        return null;
    }

    @Transactional
    public Address getAddressByAddressId(long userId, long addressId) throws Throwable{
        return addressRepository.findByAddressIdAndUserId(userId, addressId).orElseThrow(
                () -> {throw new RuntimeException("Address doesn't exist !!");}
        );
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

    @Transactional
    public void deleteByAddressId(long addressId, long userId) {
        Optional<Address> foundAddress = addressRepository.findByAddressIdAndUserId(userId, addressId);
        if(foundAddress.isPresent())
        {
            if(foundAddress.get().isDefault())
            {
                throw new RuntimeException("Default address can not be deleted !!!");
            }
            addressRepository.deleteById(foundAddress.get().getAddressId());
        }
        else
        {
            throw new RuntimeException("Address doesn't exist !!!");
        }
    }
}
