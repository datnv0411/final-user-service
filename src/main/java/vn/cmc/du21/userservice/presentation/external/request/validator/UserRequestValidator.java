package vn.cmc.du21.userservice.presentation.external.request.validator;

import vn.cmc.du21.userservice.presentation.external.request.UserRequest;

public class UserRequestValidator {
    private UserRequestValidator() {
        super();
    }
    private static String regexPatternEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    //validation method
    public static void upsertRequestValidate(UserRequest request) throws Exception {
        //check fullname
        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            throw new Exception("Field full_name required!!!");
        }
        //check dob
        if (request.getDob() == null || request.getDob().trim().isEmpty()) {
            throw new Exception("Field date_of_birth required!!!");
        }
        //check gender
        if (request.getGender() == null || request.getGender().trim().isEmpty()) {
            throw new Exception("Field gender required!!!");
        }
        //check email
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new Exception("Field email required!!!");
        }
        if (!request.getEmail().matches(regexPatternEmail)) {
            throw new Exception("Field email is not valid!!!");
        }
    }
}
