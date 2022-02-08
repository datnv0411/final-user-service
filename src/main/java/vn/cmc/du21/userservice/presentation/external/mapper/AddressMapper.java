package vn.cmc.du21.userservice.presentation.external.mapper;

import vn.cmc.du21.userservice.persistence.internal.entity.Address;
import vn.cmc.du21.userservice.presentation.external.request.AddrressRequest;
import vn.cmc.du21.userservice.presentation.external.response.AddressResponse;

public class AddressMapper {
    private AddressMapper() {super();}

    public static Address convertAddressRequestToAddress (AddrressRequest addrressRequest){
        return new Address(addrressRequest.getAddressId(), addrressRequest.isDefault()
        , addrressRequest.getTypeAddress(), addrressRequest.getFullName()
        , addrressRequest.getCellphone(), addrressRequest.getProvince()
        , addrressRequest.getDistrict(), addrressRequest.getTown()
        , addrressRequest.getSpecificAddress());
    }

    public static AddressResponse convertAddressToAddressResponse(Address address) {
        return new AddressResponse(address.getAddressId(), address.isDefault(), address.getTypeAddress(), address.getFullName()
                , address.getCellphone(), address.getProvince(), address.getDistrict(), address.getTown()
                , address.getSpecificAddress());
    }
}
