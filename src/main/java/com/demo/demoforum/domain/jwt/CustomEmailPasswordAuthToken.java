package com.demo.demoforum.domain.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

// reference는 UsernamePasswordAuthenticationToken.java
// UsernamePasswordAuthenticationToken는 DaoAuthenticationProvider
public class CustomEmailPasswordAuthToken extends AbstractAuthenticationToken {
    private final Object principal;

    private final Object credentials;

    public CustomEmailPasswordAuthToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public CustomEmailPasswordAuthToken(Object principal, Object credentials,
                                        Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
