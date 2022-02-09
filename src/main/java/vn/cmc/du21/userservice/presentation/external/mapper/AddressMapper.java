package vn.cmc.du21.userservice.presentation.external.mapper;

import vn.cmc.du21.userservice.persistence.internal.entity.Address;
import vn.cmc.du21.userservice.presentation.external.request.AddressRequest;
import vn.cmc.du21.userservice.presentation.external.response.AddressResponse;

public class AddressMapper {
    private AddressMapper() {super();}

    public static Address convertAddressRequestToAddress (AddressRequest addressRequest){
        return new Address(addressRequest.getAddressId(), addressRequest.isDefault()
        , addressRequest.getTypeAddress(), addressRequest.getFullName()
        , addressRequest.getCellphone(), addressRequest.getProvince()
        , addressRequest.getDistrict(), addressRequest.getTown()
        , addressRequest.getSpecificAddress());
    }

    public static AddressResponse convertAddressToAddressResponse(Address address) {
        return new AddressResponse(address.getAddressId(), address.isDefault(), address.getTypeAddress(), address.getFullName()
                , address.getCellphone(), address.getProvince(), address.getDistrict(), address.getTown()
                , address.getSpecificAddress());
    }
}
