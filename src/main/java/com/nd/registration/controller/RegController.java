package com.nd.registration.controller;

import com.nd.registration.dto.UserDto;
import com.nd.registration.facade.RegistrationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author alexey.shishkov@softline.com
 * @since 2024
 */
@RequiredArgsConstructor
@RestController
public class RegController {
    private final RegistrationFacade registrationFacade;

    @PostMapping("/users/sign-up")
    public void signUp(@RequestBody UserDto userDto) {
        registrationFacade.signUp(userDto);
    }

    @GetMapping("/tokens/verify")
    public void verifyToken(@RequestParam("token") UUID token) {
        registrationFacade.verifyToken(token);
    }
}
