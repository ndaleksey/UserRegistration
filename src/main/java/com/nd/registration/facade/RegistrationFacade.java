package com.nd.registration.facade;

import com.nd.registration.dto.LeadDto;
import com.nd.registration.dto.LeadStatus;
import com.nd.registration.dto.UserDto;
import com.nd.registration.service.IdmUserService;
import com.nd.registration.service.LeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * @author alexey.shishkov@softline.com
 * @since 2024
 */
@Component
@RequiredArgsConstructor
public class RegistrationFacade {
    private static final long TIMEOUT_SECONDS = 20;
    private final IdmUserService userService;
    private final LeadService leadService;

    public void signUp(UserDto userDto) {
        validate(userDto);

        userService.getByEmail(userDto.getEmail())
                .ifPresentOrElse(u -> {
                    System.out.printf("User with login [%s] already exists\n", u.getLogin());
                }, () -> {
                    var lead = LeadDto.builder()
                            .email(userDto.getEmail())
                            .orgName(userDto.getOrgName())
                            .password(userDto.getPassword())
                            .token(UUID.randomUUID())
                            .status(LeadStatus.WAITING)
                            .build();
                    leadService.create(lead);
                    System.out.printf("Sending mail with token = %s\n", lead.getToken());
                });
    }

    private void validate(UserDto userDto) {
        if (!StringUtils.hasText(userDto.getCaptcha())) {
            throw new RuntimeException("Cannot process registration request due to captcha is not presented");
        }

        System.out.println("Sending captcha to Yandex");
    }

    public void verifyToken(UUID token) {
        leadService.getByToken(token)
                .ifPresentOrElse(l -> {
                    userService.getByEmail(l.getEmail()).ifPresentOrElse(u -> {
                        System.out.printf("User with login [%s] already exists\n", u.getLogin());
                    }, () -> {
                        var user = UserDto.builder()
                                .email(l.getEmail())
                                .password(l.getPassword())
                                .build();
                        userService.create(user);
                        l.setStatus(LeadStatus.VERIFIED);
                        leadService.update(l);
                        System.out.println("Send welcome email");
                    });
                }, () -> {
                    System.out.println("Token not found");
                });
    }
}
