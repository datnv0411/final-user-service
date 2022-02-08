package vn.cmc.du21.userservice.presentation.external.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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
    //get list user
    @GetMapping("/users")
    ResponseEntity<Object> getAllUsers(@RequestParam(value = "page", required = false) String page
            , @RequestParam(value = "size", required = false) String size
            , @RequestParam(value = "sort",required = false) String sort)
    {
        if (page==null || !page.chars().allMatch(Character::isDigit) || page.equals("")) page="1";
        if (size==null || !size.chars().allMatch(Character::isDigit) || size.equals("")) size="10";
        if (sort==null || sort.equals("")) sort="userId";

        int pageInt = Integer.parseInt(page)-1;
        int sizeInt = Integer.parseInt(size);

        Page<UserResponse> listUser = userService.getAllUsers(pageInt,sizeInt,sort)
                .map(UserMapper::convertUserToUserResponse);
        return ResponseEntity.status(HttpStatus.OK).body(
                new PageResponse<Object>(
                        StatusResponse.SUCCESSFUL
                        ,"successfully"
                        , listUser.getContent()
                        , pageInt + 1
                        , listUser.getTotalPages()
                        , listUser.getTotalElements()
        ));
    }

    //get user by id
    @GetMapping("/user/{userId}")
    ResponseEntity<Object> getUser(@PathVariable Long userId,
                                   HttpServletResponse response,
                                   HttpServletRequest request) throws Throwable {
        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request);

        userService.checkUserLogin(userLogin, userId);
        UserResponse userResponse =  UserMapper.convertUserToUserResponse(
                userService.getUserById(userId)
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "found !!!",
                        userResponse
                )
        );
    }

    //insert user
    @PostMapping("/user")
    ResponseEntity<Object> addUser(@RequestBody UserRequest userRequest) throws Throwable {

        userService.checkEmailOrCellphoneExists(userRequest.getEmail(), userRequest.getCellphone());

        UserResponse userResponse =  UserMapper.convertUserToUserResponse(
                userService.addUser(UserMapper.convertUserRequestToUser(userRequest))
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "create user successfully !",
                        userResponse
                )
        );
    }

    //update user
    @PutMapping("/user/{userId}")
    ResponseEntity<Object> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long userId,
                                      HttpServletResponse response,
                                      HttpServletRequest request)
    {
        try {
            UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request);
            userService.checkUserLogin(userLogin, userId);
            userRequest.setUserId(userId);

            UserRequestValidator.upsertRequestValidate(userRequest);
            
            UserResponse userResponse = UserMapper.convertUserToUserResponse(
                    userService.updateUser(UserMapper.convertUserRequestToUser(userRequest)
                    ));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new StandardResponse<>(
                            StatusResponse.SUCCESSFUL,
                            "successfully",
                            userResponse
                    ));
        }
        catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new StandardResponse<>(
                            StatusResponse.BAD_REQUEST,
                            e.getMessage()
                    ));
        }
    }

    //delete user
    @DeleteMapping("/user/{userId}")
    ResponseEntity<Object> deleteUser(@PathVariable Long userId,
                                      HttpServletResponse response,
                                      HttpServletRequest request)
    {
        try{

            userService.deleteById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new StandardResponse<>(StatusResponse.SUCCESSFUL, "User deleted")
            );
        }catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new StandardResponse<>(StatusResponse.NOT_FOUND, e.getMessage())
            );
        }
    }
}
