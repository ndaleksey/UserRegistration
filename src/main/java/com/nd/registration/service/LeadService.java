package com.nd.registration.service;

import com.nd.registration.dto.LeadDto;
import com.nd.registration.dto.LeadStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author alexey.shishkov@softline.com
 * @since 2024
 */
@Service
public class LeadService {
    private static final long TOKEN_LIFETIME_MINUTES = 1;
    private final Set<LeadDto> leads = new HashSet<>();

    public Optional<LeadDto> getLeadByEmail(String email) {
        return leads.stream().filter(l -> l.getEmail().equals(email)).findAny();
    }

    public void create(LeadDto lead) {
        leads.add(lead);
        System.out.println("New lead was created. Lead: " + lead);
    }

    public void update(LeadDto lead) {

    }

    public Optional<LeadDto> getByToken(UUID token) {
        return leads.stream()
                .filter(l -> l.getToken().equals(token))
                .filter(l->l.getStatus() == LeadStatus.WAITING) // ???
                .filter(l -> Duration.between(l.getCreatedAt(), Instant.now()).toMinutes() < TOKEN_LIFETIME_MINUTES)
                .findAny();
    }

    public void delete(LeadDto l) {
        leads.remove(l);
    }
}
