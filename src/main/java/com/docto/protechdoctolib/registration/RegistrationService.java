package com.docto.protechdoctolib.registration;

import com.docto.protechdoctolib.user.User;
import com.docto.protechdoctolib.user.UserRole;
import com.docto.protechdoctolib.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final UserService userService;

    public RegistrationService(EmailValidator emailValidator, UserService userService) {
        this.emailValidator = emailValidator;
        this.userService = userService;
    }

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        return userService.signUpUser(
                new User(
                        request.getNom(),
                        request.getPrenom(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getPhonenumber(),
                        UserRole.USER

                ));


    }
}
