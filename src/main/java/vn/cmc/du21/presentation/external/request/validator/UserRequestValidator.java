package vn.cmc.du21.presentation.external.request.validator;


import vn.cmc.du21.presentation.external.request.UserUpsertRequest;

public class UserRequestValidator {
    private UserRequestValidator() {
        super();
    }
    private static String regexPatternEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static String regexPatternPhone = "^([0][0-9]{9})$";
    //validation method
    public static void upsertRequestValidate(UserUpsertRequest request) throws Exception {
        //check username
        if (request.getUserName() == null || request.getUserName().trim().isEmpty()) {
            throw new Exception("Field username required!!!");
        }
        if (request.getUserName().contains(" ")) {
            throw new Exception("Field username cannot include space!!!");
        }
        //check password
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new Exception("Field password required!!!");
        }
        if (request.getPassword().contains(" ")) {
            throw new Exception("Field password cannot include space!!!");
        }
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
        //check cellphone
        if (request.getCellphone() == null || request.getCellphone().trim().isEmpty()) {
            throw new Exception("Field cellphone required!!!");
        }
        if (!request.getCellphone().matches(regexPatternPhone)) {
            throw new Exception("Field cellphone is not valid!!!");
        }
        //check address
        if (request.getAddress() == null || request.getAddress().trim().isEmpty()) {
            throw new Exception("Field address required!!!");
        }
    }
}
