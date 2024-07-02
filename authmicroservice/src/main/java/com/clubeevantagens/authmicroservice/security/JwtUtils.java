package com.clubeevantagens.authmicroservice.security;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.clubeevantagens.authmicroservice.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    @Value("${jwt.token.expiry}")
    private Long expiry;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtUtils(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();

        var scopes = userDetails.getAuthorities()
                .stream()
                .map(grantedAuthority -> {
                    Role role = new Role();
                    role.setName(grantedAuthority.getAuthority());
                    return role.getName();
                })
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("clubee-security")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(userDetails.getUsername())
                .claim("scope", scopes)
                .build();

        return jwtEncoder.encode(
                        JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    public Map<String, Object> extractToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);

        Map<String, Object> payload = new HashMap<>();
        payload.put("iss", jwt.getIssuer());
        payload.put("sub", jwt.getSubject());
        payload.put("exp", jwt.getExpiresAt());
        payload.put("iat", jwt.getIssuedAt());
        payload.put("scope", jwt.getClaimAsString("scope"));

        return payload;
    }

}
