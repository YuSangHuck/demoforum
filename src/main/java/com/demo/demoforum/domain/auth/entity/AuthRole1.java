package com.demo.demoforum.domain.auth.entity;

import lombok.Getter;

@Getter
public enum AuthRole1 {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String value;

    AuthRole1(String value) {
        this.value = value;
    }
}
