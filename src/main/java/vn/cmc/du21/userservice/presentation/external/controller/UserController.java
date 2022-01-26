//package vn.cmc.du21.userservice.presentation.external.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import vn.cmc.du21.userservice.presentation.external.request.UserRequest;
//import vn.cmc.du21.userservice.presentation.external.response.UserResponse;
//import vn.cmc.du21.userservice.service.UserService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping(path = "/api/v1.0")
//public class UserController {
//    @Autowired
//    UserService userService;
//    //get list user
//    @GetMapping("/users")
//    ResponseEntity<Object> getAllUsers(@RequestParam(value = "page", required = false) String page
//            , @RequestParam(value = "size", required = false) String size
//            , @RequestParam(value = "sort",required = false) String sort)
//    {
//
//    }
//
//    //get user by id
//    @GetMapping("/user/{id}")
//    ResponseEntity<Object> getUser(@PathVariable Long id)
//    {
//
//    }
//
//    //insert user
//    @PostMapping("/user")
//    ResponseEntity<Object> addUser(@RequestBody UserRequest userRequest)
//    {
//
//    }
//
//    //update user
//    @PutMapping("/user/{id}")
//    ResponseEntity<Object> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long id)
//    {
//
//    }
//
//    //delete user
//    @DeleteMapping("/user/{id}")
//    ResponseEntity<Object> deleteUser(@PathVariable Long id)
//    {
//
//    }
//}
