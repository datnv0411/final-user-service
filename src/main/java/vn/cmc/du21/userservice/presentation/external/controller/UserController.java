package vn.cmc.du21.userservice.presentation.external.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
                        StatusResponse.SUCCESSFUL,
                        "successfully",
                        listUser
                        ,pageInt
                        ,userService.totalPage(pageInt,sizeInt,sort)
                        , userService.totalRecord(pageInt,sizeInt,sort)));
    }

    //get user by id
    @GetMapping("/user/{id}")
    ResponseEntity<Object> getUser(@PathVariable Long id)
    {


            UserResponse foundUser = UserMapper.convertUserToUserResponse(userService.getUserById(id));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new StandardResponse<>(
                            StatusResponse.SUCCESSFUL,
                            "successfully",
                            foundUser
                    ));


//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                new StandardResponse<>(
//                        StatusResponse.NOTFOUND,
//                        "not found user"
//                )
//        );
    }

    //insert user
    @PostMapping("/user")
    ResponseEntity<Object> addUser(@RequestBody(required = false) UserRequest userRequest)
    {
          UserResponse userResponse = UserMapper.convertUserToUserResponse(
                    userService.addUser(UserMapper.convertUserRequestToUser(userRequest)
                    ));
          UserResponse u = userResponse;
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new StandardResponse<>(
                            StatusResponse.SUCCESSFUL,
                            "successfully",
                            userResponse
                    ));
    }

    //update user
//    @PutMapping("/user/{id}")
//    ResponseEntity<Object> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long id)
//    {
//
//    }

    //delete user
//    @DeleteMapping("/user/{id}")
//    ResponseEntity<Object> deleteUser(@PathVariable Long id)
//    {
//
//    }
}
