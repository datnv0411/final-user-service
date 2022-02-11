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
import vn.cmc.du21.userservice.presentation.external.mapper.UserMapper;
import vn.cmc.du21.userservice.presentation.external.request.UserRequest;
import vn.cmc.du21.userservice.presentation.external.request.validator.UserRequestValidator;
import vn.cmc.du21.userservice.presentation.external.response.UserResponse;
import vn.cmc.du21.userservice.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api/v1.0")
public class UserController {
    @Autowired
    UserService userService;

    //get user by id
    @GetMapping("/user/{userId}")
    ResponseEntity<Object> getUser(@PathVariable Long userId,
                                   HttpServletResponse response, HttpServletRequest request) throws Throwable {

        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request);

        userService.checkUserLogin(userLogin, userId);
        UserResponse userResponse =  UserMapper.convertUserToUserResponse(
                userService.getUserById(userId)
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Found !!!",
                        userResponse
                )
        );
    }

    //update user
    @PutMapping("/user/{userId}")
    ResponseEntity<Object> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long userId,
                                      HttpServletResponse response, HttpServletRequest request) throws Throwable{

        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request);
        userService.checkUserLogin(userLogin, userId);
        userRequest.setUserId(userId);

        UserRequestValidator.upsertRequestValidate(userRequest);

        UserResponse userResponse = UserMapper.convertUserToUserResponse(
                userService.updateUser(UserMapper.convertUserRequestToUser(userRequest))
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Successfully",
                        userResponse)
        );
    }
}
