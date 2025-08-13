package com.thinkIndia.backend.security; // Use your actual package name

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "sdkpoLjgsJKdOF23SDs0400sdl=GGstwwbxB,/sggw";
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    /**
     * ✅ CHANGED: This method now accepts user authorities to include them in the token.
     * The expiration time is now hardcoded here for consistency (e.g., 24 hours).
     */
    public String generateToken(String email, Collection<? extends GrantedAuthority> authorities) {
        // Convert the authorities Collection to a simple List of strings (e.g., ["ROLE_ADMIN"])
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(email)
                .claim("roles", roles) // ✅ ADDED: Roles are added as a custom claim.
                .issuedAt(new Date(now))
                .expiration(new Date(now + 1000 * 60 * 60 * 24)) // 24-hour expiration
                .signWith(key)
                .compact();
    }

    /**
     * ✅ CHANGED: This method now has improved error handling to log the specific
     * reason why a token is invalid.
     */
    public String validateAndExtractUsername(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            System.err.println("JWT Validation Error: Token has expired - " + e.getMessage());
        } catch (SignatureException e) {
            System.err.println("JWT Validation Error: Signature is invalid - " + e.getMessage());
        } catch (Exception e) {
            // Catch any other JWT-related exception
            System.err.println("JWT Validation Error: " + e.getMessage());
        }
        return null;
    }
}