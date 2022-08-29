package com.demo.demoforum.feature.authority;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, UserAuth> {
    Optional<Authority> findByAuthorityName(UserAuth authorityName);
}