package vn.cmc.du21.userservice.presentation.internal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api/v1.0/authentication")
public class AuthorizationController {
    // Verify
//    @GetMapping("/verify")
//    ResponseEntity<Object> verify(HttpServletResponse response, HttpServletRequest request)
//    {
//
//    }
}
