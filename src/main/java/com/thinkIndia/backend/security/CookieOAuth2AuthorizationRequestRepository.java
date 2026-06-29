package com.thinkIndia.backend.security;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.SerializationUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Stores the OAuth2 authorization request in a browser cookie instead of the
 * server-side HttpSession. This makes the OAuth2 flow stateless and avoids
 * the "authorization_request_not_found" error that occurs when the session
 * is lost between the initial redirect to Google and the callback.
 */
public class CookieOAuth2AuthorizationRequestRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    private static final int COOKIE_EXPIRE_SECONDS = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        System.out.println("[OAUTH] loadAuthorizationRequest called");
        Cookie cookie = getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        if (cookie == null) {
            System.out.println("[OAUTH] No authorization request cookie found");
            return null;
        }
        try {
            OAuth2AuthorizationRequest authRequest = deserialize(cookie.getValue());
            System.out.println("[OAUTH] Loaded authorization request from cookie");
            return authRequest;
        } catch (Exception e) {
            System.err.println("[OAUTH] Failed to deserialize authorization request from cookie: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
            HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            System.out.println("[OAUTH] Removing authorization request cookie");
            deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            return;
        }
        System.out.println("[OAUTH] Saving authorization request to cookie, state=" + authorizationRequest.getState());
        String serialized = serialize(authorizationRequest);
        Cookie cookie = new Cookie(OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, serialized);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(COOKIE_EXPIRE_SECONDS);
        cookie.setSecure(true);
        // SameSite=None is required for cross-site POST callbacks from Google
        // Must be set via header since Java Cookie API doesn't support SameSite directly
        response.addHeader("Set-Cookie",
                OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME + "=" + serialized
                        + "; Path=/; HttpOnly; Secure; SameSite=None; Max-Age=" + COOKIE_EXPIRE_SECONDS);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
            HttpServletResponse response) {
        System.out.println("[OAUTH] removeAuthorizationRequest called");
        OAuth2AuthorizationRequest authorizationRequest = loadAuthorizationRequest(request);
        if (authorizationRequest != null) {
            deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        }
        return authorizationRequest;
    }

    private static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    private static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    private static String serialize(Object object) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
    }

    @SuppressWarnings("unchecked")
    private static <T> T deserialize(String base64) {
        return (T) SerializationUtils.deserialize(Base64.getUrlDecoder().decode(base64));
    }
}
