package vn.cmc.du21.userservice.presentation.external.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.cmc.du21.userservice.common.RandomOtpUtil;
import vn.cmc.du21.userservice.common.SmsSender;
import vn.cmc.du21.userservice.common.restful.StandardResponse;
import vn.cmc.du21.userservice.common.restful.StatusResponse;
import vn.cmc.du21.userservice.presentation.external.mapper.SessionMapper;
import vn.cmc.du21.userservice.presentation.external.mapper.UserMapper;
import vn.cmc.du21.userservice.presentation.external.request.OtpRequest;
import vn.cmc.du21.userservice.presentation.external.response.SessionResponse;
import vn.cmc.du21.userservice.presentation.external.response.UserResponse;
import vn.cmc.du21.userservice.service.AuthenticationService;
import vn.cmc.du21.userservice.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/api/v1.0/authentication")
public class AuthenticationController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;

    // Logout
    @GetMapping("/logout")
    ResponseEntity<Object> logout(HttpServletResponse response, HttpServletRequest request) throws Throwable {

        log.info("Mapped logout method {{GET: /logout}}");
        String[] arr = request.getHeader("Authorization").split(" ");
        String token = arr[1];

        authenticationService.disableToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Logout successfully !!!"
                )
        );
    }

    //Generate Otp - nên để post, id để là uuid
    @PostMapping("/generate-otp")
    ResponseEntity<Object> generate(@RequestBody OtpRequest otpRequest,
                                    HttpServletResponse response, HttpServletRequest request) {

        log.info("Mapped generate method {{GET: /generate-otp}}");
        String otp = RandomOtpUtil.createOtp();

        SmsSender.sendOtp(otpRequest.getCellphone(), otp);

        authenticationService.addOtp(otp, otpRequest.getCellphone());
        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Check your phone !!!"
                )
        );
    }

    //VerifyOtp
    @PostMapping("/login")
    ResponseEntity<Object> verifyOtp(@RequestBody OtpRequest otpRequest,
                                     HttpServletResponse response, HttpServletRequest request) throws Throwable {

        log.info("Mapped verifyOtp method {{Post: /login}}");
        authenticationService.checkOtp(otpRequest.getOtpPass(), otpRequest.getCellphone());

        UserResponse userResponse = UserMapper.convertUserToUserResponse(
                userService.checkCellphoneExistsInSessionTable(otpRequest.getCellphone())
        );

        long deviceId = 1L;
        //create session for user
        SessionResponse sessionResponse = SessionMapper.convertSessionToSessionResponse(
                authenticationService.upsertSession(userResponse.getUserId(), deviceId)
        );

        //create user + session response
        List<Object> userSession = new ArrayList<>();
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
}
