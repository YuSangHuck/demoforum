package com.demo.demoforum.domain.auth.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum AuthRole2 {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");
    private static final Map<String, AuthRole2> lookup = new HashMap<>();

    static {
        for (AuthRole2 auth : AuthRole2.values()) {
            lookup.put(auth.abbreviation, auth);
        }

    }

    private final String abbreviation;

    AuthRole2(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static AuthRole2 get(String abbreviation) {
        return lookup.get(abbreviation);
    }

    public static boolean containsKey(String abbreviation) {
        return lookup.containsKey(abbreviation);
    }
}
