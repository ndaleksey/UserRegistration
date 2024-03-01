package com.nd.registration.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

/**
 * @author alexey.shishkov@softline.com
 * @since 2024
 */
@Data
@Builder
public class LeadDto {
    private String orgName;
    private String email;
    private String password;
    private UUID token;
    private LeadStatus status;
    private Instant createdAt;
}
