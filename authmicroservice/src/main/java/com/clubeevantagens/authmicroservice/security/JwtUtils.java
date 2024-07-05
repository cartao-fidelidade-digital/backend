package com.clubeevantagens.authmicroservice.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.clubeevantagens.authmicroservice.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    @Value("${jwt.token.expiry}")
    private Long expiry;

    @Value("${jwt.token.refresh.expiry}")
    private Long expiryRefreshToken;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtUtils(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    // GERAR ACCESS TOKEN
    public String generateAccessToken(UserDetails userDetails) {
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

    // EXTRAIR ACCESS TOKEN
    public Map<String, String> extractAccessToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);

        Map<String, String> payload = new HashMap<>();
        payload.put("iss", jwt.getClaimAsString("iss"));
        payload.put("sub", jwt.getSubject());
        payload.put("exp", jwt.getExpiresAt().toString());
        payload.put("iat", jwt.getIssuedAt().toString());
        payload.put("scope", jwt.getClaimAsString("scope"));

        return payload;
    }

    // GERAR REFRESH TOKEN
    public Map<String,String> generateRefreshToken(String accessTokenExpired) {

        var accessTokenMap = extractAccessToken(accessTokenExpired);// extrair "accessToken"
        var userId = accessTokenMap.get("sub");
        var accessIssuedAt = accessTokenMap.get("iat");
        var accessExpiresAt = accessTokenMap.get("exp");

        Instant now = Instant.now();

        var key = 1000 + new Random().nextInt(9000);// Gera um número aleatório entre 1000 e 9999 para "Key"

        var signatureRaw = ""+key + accessIssuedAt + accessExpiresAt;// assinatura crua
        var signaruteEncript = new BCryptPasswordEncoder().encode(signatureRaw);// assinatura criptografada

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("clubee-security")
                .issuedAt(Instant.parse(accessIssuedAt))
                .expiresAt(LocalDateTime.now().plusDays(expiryRefreshToken).toInstant(ZoneOffset.UTC))
                .subject(userId)
                .claim("key", key)
                .claim("signature", signaruteEncript)
                .build();

        var refreshToken = jwtEncoder.encode(
                        JwtEncoderParameters.from(claims))
                .getTokenValue();


        Map<String,String> retorno = new HashMap<>();// retorno
        retorno.put("refreshToken", refreshToken);
        retorno.put("key", String.valueOf(key));

        return retorno;
    }


    // EXTRAIR REFRESH TOKEN
    public Map<String, String> extractRefreshToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);

        Map<String, String> payload = new HashMap<>();
        payload.put("iss", jwt.getClaimAsString("iss"));
        payload.put("sub", jwt.getSubject());
        payload.put("exp", jwt.getExpiresAt().toString());
        payload.put("iat", jwt.getIssuedAt().toString());
        payload.put("key", jwt.getClaimAsString("key"));
        payload.put("signature", jwt.getClaimAsString("signature"));

        return payload;
    }




}
