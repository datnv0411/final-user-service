package vn.cmc.du21.userservice.presentation.internal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.cmc.du21.userservice.common.restful.StandardResponse;
import vn.cmc.du21.userservice.common.restful.StatusResponse;
import vn.cmc.du21.userservice.presentation.external.mapper.UserMapper;
import vn.cmc.du21.userservice.presentation.external.response.UserResponse;
import vn.cmc.du21.userservice.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api/v1.0/authentication")
public class AuthorizationController {
    @Autowired
    AuthenticationService authenticationService;
    // Verify
    @GetMapping("/verify")
    ResponseEntity<Object> verify(@RequestParam(value = "token") String token,
                                  HttpServletResponse response, HttpServletRequest request) throws Throwable {

        if(authenticationService.getUserByToken(token) != null)
        {
            UserResponse userResponse = UserMapper.convertUserToUserResponse(authenticationService.getUserByToken(token));
            return ResponseEntity.status(HttpStatus.OK).body(
                            userResponse
            );
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new StandardResponse<>(
                        StatusResponse.BAD_REQUEST,
                        "Error token !!!"
                )
        );
    }
}