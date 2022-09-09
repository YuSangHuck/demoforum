package com.demo.demoforum.domain.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "authority")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority {
    @Id
    @Enumerated(EnumType.STRING)
    private AuthRole2 authorityName;

    public String getAuthorityName() {
        return authorityName.toString();
    }
}