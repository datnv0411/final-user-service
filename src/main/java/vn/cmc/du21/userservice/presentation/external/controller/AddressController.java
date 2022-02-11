package vn.cmc.du21.userservice.presentation.external.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.cmc.du21.userservice.common.restful.JwtTokenProvider;
import vn.cmc.du21.userservice.common.restful.PageResponse;
import vn.cmc.du21.userservice.common.restful.StandardResponse;
import vn.cmc.du21.userservice.common.restful.StatusResponse;
import vn.cmc.du21.userservice.presentation.external.mapper.AddressMapper;
import vn.cmc.du21.userservice.presentation.external.request.AddressRequest;
import vn.cmc.du21.userservice.presentation.external.response.AddressResponse;
import vn.cmc.du21.userservice.presentation.external.response.UserResponse;
import vn.cmc.du21.userservice.service.AddressService;
import vn.cmc.du21.userservice.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api/v1.0")
public class AddressController {
    @Autowired
    AddressService addressService;
    @Autowired
    UserService userService;

    //get all address by userId
    @GetMapping("/address")
    ResponseEntity<Object> getAllAddress(@RequestParam(value = "page", required = false) String page
            , @RequestParam(value = "size", required = false) String size
            , @RequestParam(value = "sort",required = false) String sort
            , HttpServletResponse response, HttpServletRequest request) throws Throwable {

        if (page==null || !page.chars().allMatch(Character::isDigit) || page.equals("")) page="1";
        if (size==null || !size.chars().allMatch(Character::isDigit) || size.equals("")) size="10";
        if (sort==null || sort.equals("")) sort="addressId";

        int pageInt = Integer.parseInt(page)-1;
        int sizeInt = Integer.parseInt(size);

        UserResponse userLogin;
        try {
            userLogin = JwtTokenProvider.getInfoUserFromToken(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new StandardResponse<>(
                            StatusResponse.UNAUTHORIZED,
                            "Bad token!!!"
                    )
            );
        }

        Page<AddressResponse> listAddress = addressService.getAllAddress(userLogin.getUserId(), pageInt, sizeInt, sort)
                .map(AddressMapper::convertAddressToAddressResponse);
        return ResponseEntity.status(HttpStatus.OK).body(
                new PageResponse<Object>(
                        StatusResponse.SUCCESSFUL
                        ,"Successfully"
                        , listAddress.getContent()
                        , pageInt + 1
                        , listAddress.getTotalPages()
                        , listAddress.getTotalElements()
                )
        );
    }

    //get address by id
    @GetMapping("/address/{addressId}")
    ResponseEntity<Object> getUser(@PathVariable Long addressId,
                                   HttpServletResponse response, HttpServletRequest request) throws Throwable {

        UserResponse userLogin;
        try {
            userLogin = JwtTokenProvider.getInfoUserFromToken(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new StandardResponse<>(
                            StatusResponse.UNAUTHORIZED,
                            "Bad token!!!"
                    )
            );
        }

        AddressResponse addressResponse =  AddressMapper.convertAddressToAddressResponse(
                addressService.getAddressByAddressId(userLogin.getUserId(), addressId)
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Found !!!",
                        addressResponse
                )
        );
    }


    //insert address
    @PostMapping("/address")
    ResponseEntity<Object> addAddress(@RequestBody AddressRequest addressRequest,
                                      HttpServletResponse response, HttpServletRequest request) throws Throwable{

        UserResponse userLogin;
        try {
            userLogin = JwtTokenProvider.getInfoUserFromToken(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new StandardResponse<>(
                            StatusResponse.UNAUTHORIZED,
                            "Bad token!!!"
                    )
            );
        }

        AddressResponse addressResponse =  AddressMapper.convertAddressToAddressResponse(
                addressService.addAddress(AddressMapper.convertAddressRequestToAddress(addressRequest),
                        userLogin.getUserId())
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Create address successfully !",
                        addressResponse
                )
        );
    }

    //update address
    @PutMapping("/address/{addressId}")
    ResponseEntity<Object> updateAddress(@RequestBody AddressRequest addressRequest, @PathVariable Long addressId,
                                         HttpServletResponse response, HttpServletRequest request) throws Throwable {

        UserResponse userLogin;
        try {
            userLogin = JwtTokenProvider.getInfoUserFromToken(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new StandardResponse<>(
                            StatusResponse.UNAUTHORIZED,
                            "Bad token!!!"
                    )
            );
        }

        addressRequest.setAddressId(addressId);
        addressRequest.setUserId(userLogin.getUserId());
        AddressResponse addressResponse = AddressMapper.convertAddressToAddressResponse(
            addressService.updateAddress(
                    AddressMapper.convertAddressRequestToAddress(addressRequest),
                    userLogin.getUserId())
        );
        return ResponseEntity.status(HttpStatus.OK).body(
            new StandardResponse<>(
                    StatusResponse.SUCCESSFUL,
                    "Successfully",
                    addressResponse
            )
        );
    }

    //delete address
    @DeleteMapping("/address/{addressId}")
    ResponseEntity<Object> deleteAddress(@PathVariable Long addressId,
                                         HttpServletResponse response, HttpServletRequest request) throws Throwable{

        UserResponse userLogin;
        try {
            userLogin = JwtTokenProvider.getInfoUserFromToken(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new StandardResponse<>(
                            StatusResponse.UNAUTHORIZED,
                            "Bad token!!!"
                    )
            );
        }

        addressService.deleteByAddressId(addressId, userLogin.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(StatusResponse.SUCCESSFUL,
                        "Address deleted")
        );
    }
}
