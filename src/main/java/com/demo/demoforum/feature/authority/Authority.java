package com.demo.demoforum.feature.authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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
    private UserAuth authorityName;

    public String getAuthorityName() {
        return authorityName.toString();
    }
}