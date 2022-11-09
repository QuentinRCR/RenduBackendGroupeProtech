package com.docto.protechdoctolib.logging;

import com.docto.protechdoctolib.logging.token.ConnectionTokenService;
import com.docto.protechdoctolib.registration.EmailValidator;
import com.docto.protechdoctolib.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

    private final UserService userService;
    private final ConnectionTokenService connectionTokenService ;

    private final EmailValidator emailValidator;

    public LoggingService(UserService userService, ConnectionTokenService connectionTokenService, EmailValidator emailValidator) {

        this.userService = userService;
        this.connectionTokenService = connectionTokenService;
        this.emailValidator = emailValidator;
    }

    public String logging(LoggingRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        return "AAA";
    }
}
