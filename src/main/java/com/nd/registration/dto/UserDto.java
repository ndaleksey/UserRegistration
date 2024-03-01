package com.nd.registration.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author alexey.shishkov@softline.com
 * @since 2024
 */
@Data
@Builder
public class UserDto {
    private String email;
    private String orgName;
    private String login;
    private String password;
    private String captcha;
}
