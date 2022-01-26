package vn.cmc.du21.userservice.presentation.external.mapper;

import vn.cmc.du21.userservice.common.DateTimeUtil;
import vn.cmc.du21.userservice.persistence.internal.entity.User;
import vn.cmc.du21.userservice.presentation.external.request.UserRequest;
import vn.cmc.du21.userservice.presentation.external.response.UserResponse;

import java.sql.Date;

public class UserMapper {
    private UserMapper()
    {
        super();
    }

    public static UserRequest convertUserToUserRequest(User user)
    {
        return new UserRequest(user.getUserId(), user.getFullName(), DateTimeUtil.dateSqlToString(user.getDob()),
                user.getGender(), user.getEmail(), user.getCellphone());
    }

    public static User convertUserRequestToUser(UserRequest userRequest)
    {
        java.sql.Date dob = DateTimeUtil.stringToDateSql(userRequest.getDob());
        return new User(userRequest.getUserId(), userRequest.getFullName(), dob,
                userRequest.getGender(), userRequest.getEmail(), userRequest.getCellphone());
    }

    public static UserResponse convertUserToUserResponse(User user)
    {
        return new UserResponse(user.getUserId(), user.getFullName(), DateTimeUtil.dateSqlToString(user.getDob()),
                user.getGender(), user.getEmail(), user.getCellphone());
    }
}
