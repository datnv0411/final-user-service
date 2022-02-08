package vn.cmc.du21.userservice.presentation.external.mapper;

import vn.cmc.du21.userservice.persistence.internal.entity.Address;
import vn.cmc.du21.userservice.presentation.external.response.AddressResponse;
import vn.cmc.du21.userservice.presentation.external.response.UserResponse;

public class AddressMapper {
    private AddressMapper()
    {
        super();
    }

    public static AddressResponse convertAddressToAddressResponse(Address address) {
        return new AddressResponse(address.getAddressId(), address.isDefault(), address.getTypeAddress(), address.getFullName()
                                , address.getCellphone(), address.getProvince(), address.getDistrict(), address.getTown()
                                , address.getSpecificAddress());
    }
}
