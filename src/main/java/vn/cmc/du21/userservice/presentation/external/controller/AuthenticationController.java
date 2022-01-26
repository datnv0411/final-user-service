package vn.cmc.du21.userservice.presentation.external.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.cmc.du21.userservice.common.restful.StandardResponse;
import vn.cmc.du21.userservice.common.restful.StatusResponse;
import vn.cmc.du21.userservice.presentation.external.mapper.SessionMapper;
import vn.cmc.du21.userservice.presentation.external.mapper.UserMapper;
import vn.cmc.du21.userservice.presentation.external.request.UserRequest;
import vn.cmc.du21.userservice.presentation.external.response.SessionResponse;
import vn.cmc.du21.userservice.presentation.external.response.UserResponse;
import vn.cmc.du21.userservice.service.AuthenticationService;
import vn.cmc.du21.userservice.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1.0/authentication")
public class AuthenticationController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;
    // SignIn
    @PostMapping("/login")
    ResponseEntity<Object> login(@RequestParam(value = "cellphone") String cellphone, HttpServletResponse response, HttpServletRequest request)
    {

        UserResponse userResponse;

        if(userService.findByCellphone(cellphone) == null)
        {
            UserRequest userRequest = new UserRequest();
            userRequest.setCellphone(cellphone);
            // add new user with role default 'User'
            userResponse =  UserMapper.convertUserToUserResponse(
                    userService.addUser(UserMapper.convertUserRequestToUser(userRequest))
            );
        }
        else
        {
            // get user
            userResponse = UserMapper.convertUserToUserResponse(
                    userService.findByCellphone(cellphone)
            );
        }

        long deviceId = 1L;
        //create session for user
        SessionResponse sessionResponse = SessionMapper.convertSessionToSessionResponse(
                authenticationService.upsertSession(userResponse.getUserId(), deviceId)
        );
        
        //create user + session response
        List userSession = new ArrayList();
        userSession.add(userResponse);
        userSession.add(sessionResponse);

        response.setHeader("Authorization", "Bearer " + sessionResponse.getToken());

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Login successfully !",
                        userSession
                )
        );
    }

    // Logout
    @GetMapping("/logout")
    ResponseEntity<Object> logout(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String[] arr = request.getHeader("Authorization").split(" ");
        String token = arr[1];

        authenticationService.disableToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Logout successfully !"
                )
        );
    }

    //Generate Otp

    //VerifyOtp
}
