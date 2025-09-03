package com.thinkIndia.backend.security;

import java.io.IOException;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.thinkIndia.backend.entities.User;
import com.thinkIndia.backend.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    private UserService userService;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;

    public OAuthAuthenticationSuccessHandler(JwtUtil jwtUtil,@Lazy PasswordEncoder passwwEncoder, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwwEncoder;
    }
    

    @Override
public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                    Authentication authentication) throws IOException, ServletException {
    try {
        // --- ALL OF YOUR EXISTING LOGIC GOES INSIDE THIS TRY BLOCK ---
        DefaultOAuth2User oAuthUser = (DefaultOAuth2User) authentication.getPrincipal();
        String name = oAuthUser.getAttribute("name"); // Using getAttribute for name as well for consistency
        String email = oAuthUser.getAttribute("email");

        System.out.println("OAuth Success: " + name + " | " + email);

        User user;
        try {
            user = (User) userService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            user = null;
        }

        if (user == null) {
            System.out.println("Creating new user for email: " + email);
            String encodedPassword = passwordEncoder.encode("a-very-long-random-password-for-oauth-users");
            user = new User(name, email, encodedPassword, email.equals("achirjain11@gmail.com"));
            user = userService.saveUser(user);
            System.out.println("Successfully saved new user: " + user);
        }

        String jwtToken = jwtUtil.generateToken(user.getEmail(), user.getAuthorities(), 15L);
        System.out.println("Generated JWT Token successfully.");

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

                String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/")
                .queryParam("token", jwtToken)
                .queryParam("isAdmin", isAdmin)
                .build().toUriString();
        System.out.println("Redirecting to: " + redirectUrl);

        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
        // --- END OF EXISTING LOGIC ---

    } catch (Exception e) {
        // THIS WILL CATCH ANY UNEXPECTED ERROR
        System.err.println("!!!!!!!!!! CRITICAL OAUTH FAILURE !!!!!!!!!!");
        e.printStackTrace(); // This prints the full error stack trace to the log

        // Redirect to a generic error page so the user isn't stuck
        String errorUrl = UriComponentsBuilder.fromUriString("http://localhost:5173")
                              .queryParam("error", "LoginProcessingFailed")
                              .build().toUriString();
        new DefaultRedirectStrategy().sendRedirect(request, response, errorUrl);
    }
}

}
