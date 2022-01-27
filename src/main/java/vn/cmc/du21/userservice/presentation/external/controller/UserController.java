package vn.cmc.du21.userservice.presentation.external.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.userservice.common.restful.PageResponse;
import vn.cmc.du21.userservice.common.restful.StandardResponse;
import vn.cmc.du21.userservice.common.restful.StatusResponse;
import vn.cmc.du21.userservice.presentation.external.mapper.UserMapper;
import vn.cmc.du21.userservice.presentation.external.request.UserRequest;
import vn.cmc.du21.userservice.presentation.external.response.UserResponse;
import vn.cmc.du21.userservice.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


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
        if (page==null) page="1";
        if (size==null) size="10";
        if (sort==null) sort="userId";

        int pageInt = Integer.parseInt(page)-1;
        int sizeInt = Integer.parseInt(size);

        List<UserResponse> listUser = userService.getAllUsers(pageInt,sizeInt,sort)
                .stream()
                .map(UserMapper::convertUserToUserResponse)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(
                new PageResponse<Object>(
                        StatusResponse.SUCCESSFUL
                        ,"successfully"
                        , listUser
                        , pageInt + 1
                        , userService.totalPage(pageInt, sizeInt, sort)
                        , userService.totalRecord(pageInt, sizeInt, sort))
        );
    }

    //get user by id
    @GetMapping("/user/{id}")
    ResponseEntity<Object> getUser(@PathVariable Long id) throws Throwable {

//        final String uri = "localhost:8080/api/v1.0/authentication/verify";
//
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(uri, String.class);

        UserResponse userResponse =  UserMapper.convertUserToUserResponse(
                userService.getUserById(id)
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
    @PutMapping("/user/{id}")
    ResponseEntity<Object> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long id)
    {
        try {
            userRequest.setUserId(id);
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
    @DeleteMapping("/user/{id}")
    ResponseEntity<Object> deleteUser(@PathVariable Long id)
    {
        try{
            userService.deleteById(id);
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
