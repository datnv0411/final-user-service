package vn.cmc.du21.userservice.presentation.external.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.cmc.du21.userservice.common.JwtTokenProvider;
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
@Slf4j
@RequestMapping(path = "/api/v1.0")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    Environment env;

    //get user by id
    @GetMapping("/user/my-info")
    ResponseEntity<Object> getUser(HttpServletResponse response, HttpServletRequest request) throws Throwable {

        log.info("Mapped getUser method {{GET: /user/{userId}}}");
        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);

        UserResponse userResponse =  UserMapper.convertUserToUserResponse(
                userService.getUserById(userLogin.getUserId())
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
    @PutMapping("/user/update")
    ResponseEntity<Object> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long userId,
                                      HttpServletResponse response, HttpServletRequest request) throws Throwable{

        log.info("Mapped updateUser {{PUT: /user/{userId}}}");
        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);

        userRequest.setUserId(userLogin.getUserId());

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