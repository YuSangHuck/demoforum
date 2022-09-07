package com.demo.demoforum.feature.authority;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum UserAuth {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");
    private final String abbreviation;
    private static final Map<String, UserAuth> lookup = new HashMap<>();

    static {
        for (UserAuth auth : UserAuth.values()) {
            lookup.put(auth.abbreviation, auth);
        }

    }

    UserAuth(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static UserAuth get(String abbreviation) {
        return lookup.get(abbreviation);
    }

    public static boolean containsKey(String abbreviation) {
        return lookup.containsKey(abbreviation);
    }
}
