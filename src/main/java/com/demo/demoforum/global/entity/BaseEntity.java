package com.demo.demoforum.global.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public abstract class BaseEntity extends BaseTimeEntity {
    @Column(nullable = false, updatable = false)
    @CreatedBy
    protected String createdBy;
    @Column(nullable = false)
    @LastModifiedBy
    protected String lastModifiedBy;
}
