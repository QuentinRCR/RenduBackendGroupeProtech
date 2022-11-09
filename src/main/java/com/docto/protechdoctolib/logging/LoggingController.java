package com.docto.protechdoctolib.logging;

import com.docto.protechdoctolib.registration.RegistrationRequest;
import com.docto.protechdoctolib.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/logging")
public class LoggingController {

    @Autowired
    private LoggingService loggingService;

    @PostMapping
    public String logging(@RequestBody LoggingRequest request) {
        return loggingService.logging(request);
    }

}
