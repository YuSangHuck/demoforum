package com.demo.demoforum.domain.auth.repository;

import com.demo.demoforum.domain.auth.entity.AuthRole2;
import com.demo.demoforum.domain.auth.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, AuthRole2> {
    Optional<Authority> findByAuthorityName(AuthRole2 authorityName);
}