package com.auth.jwt.security;

import com.auth.jwt.controller.request.ServiceRequest;
import com.auth.jwt.entity.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    private final RouteValidator routeValidator;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(AuthUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("id", user.getId());
        claims.put("role", user.getRole());
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validate(String token, ServiceRequest request) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
        } catch (Exception ex) {
            return false;
        }

        if(!isAdmin(token) && routeValidator.isAdmin(request))
            return false;
        return true;
    }

    private boolean isAdmin(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .get("role")
                .equals("admin");
    }

    public String getUserNameFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception exception) {
            return "Bad token";
        }
    }
}
