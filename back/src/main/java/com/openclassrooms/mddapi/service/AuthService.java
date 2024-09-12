package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.security.service.UserDetailsImpl;
import com.openclassrooms.mddapi.service.interfaces.IAuthService;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
/**
 * La classe AuthService est le service pour l'authentification.
 * @see IAuthService
 */
public class AuthService implements IAuthService {

    @Override
    public Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        return cookie;
    }

    @Override
    public List<String> getRoles(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getUserId(UserDetailsImpl userDetails) {
        return userDetails.getId();
    }
}
