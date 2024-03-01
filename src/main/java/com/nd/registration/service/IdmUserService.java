package com.nd.registration.service;

import com.nd.registration.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author alexey.shishkov@softline.com
 * @since 2024
 */
@Service
public class IdmUserService {
    private final List<UserDto> users = new ArrayList<>();

    public Optional<UserDto> getByEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equals(email)).findAny();
    }

    public void create(UserDto user) {
        users.add(user);
        System.out.println("New user was created: " + user);
    }
}
