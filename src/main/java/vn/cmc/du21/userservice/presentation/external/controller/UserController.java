package vn.cmc.du21.userservice.presentation.external.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.cmc.du21.userservice.common.restful.StandardResponse;
import vn.cmc.du21.userservice.common.restful.StatusResponse;
import vn.cmc.du21.userservice.presentation.external.mapper.UserMapper;
import vn.cmc.du21.userservice.presentation.external.request.UserRequest;
import vn.cmc.du21.userservice.presentation.external.response.UserResponse;
import vn.cmc.du21.userservice.service.UserService;




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
return null;
    }

    //get user by id
    @GetMapping("/user/{id}")
    ResponseEntity<Object> getUser(@PathVariable Long id)
    {
        return null;
    }

    //insert user
    @PostMapping("/user")
    ResponseEntity<Object> addUser(@RequestBody UserRequest userRequest)
    {
        return null;
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
                            StatusResponse.BADREQUEST,
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
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new StandardResponse<>(StatusResponse.NOTFOUND, e.getMessage())
                );
            }

    }
}
