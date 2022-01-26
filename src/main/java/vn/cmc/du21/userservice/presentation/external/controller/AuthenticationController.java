package vn.cmc.du21.userservice.presentation.external.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.cmc.du21.userservice.presentation.external.mapper.UserMapper;
import vn.cmc.du21.userservice.presentation.external.request.SessionRequest;
import vn.cmc.du21.userservice.presentation.external.request.UserRequest;
import vn.cmc.du21.userservice.presentation.external.response.UserResponse;
import vn.cmc.du21.userservice.service.AuthenticationService;
import vn.cmc.du21.userservice.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    }

    // Logout
    @GetMapping("/logout")
    ResponseEntity<Object> logout(HttpServletResponse response, HttpServletRequest request)
    {

    }
}
