package com.qwammy.javaflupskilling_week_5.security;

import com.qwammy.javaflupskilling_week_5.options.JwtTokenOptions;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JWTGenerator {

    private final JwtTokenOptions jwtTokenOptions;

    @Autowired
    public JWTGenerator(JwtTokenOptions jwtTokenOptions) {
        this.jwtTokenOptions = jwtTokenOptions;
    }


    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        List<String> roles = authentication.getAuthorities().stream()
                .map(Object::toString)
                .toList();

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime() + jwtTokenOptions.getJwtExpirationMs())))
                .signWith(key())
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        return Jwts.parser().verifyWith((SecretKey) key()).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            // Log error
        }
        return false;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                jwtTokenOptions.getSecret()
        ));
    }
}
