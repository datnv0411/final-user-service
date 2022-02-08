package vn.cmc.du21.userservice.presentation.external.mapper;

import vn.cmc.du21.userservice.persistence.internal.entity.Address;
import vn.cmc.du21.userservice.presentation.external.request.AddrressRequest;

public class AddressMapper {
    private AddressMapper() {super();}

    public static Address convertAddressRequestToAddress (AddrressRequest addrressRequest){
        return new Address(addrressRequest.getAddressId(), addrressRequest.isDefault()
        , addrressRequest.getTypeAddress(), addrressRequest.getFullName()
        , addrressRequest.getCellphone(), addrressRequest.getProvince()
        , addrressRequest.getDistrict(), addrressRequest.getTown()
        , addrressRequest.getSpecificAddress());
    }
}
