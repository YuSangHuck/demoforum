package com.demo.demoforum.domain.member.entity;

import com.demo.demoforum.domain.answer.entity.Answer;
import com.demo.demoforum.domain.auth.entity.Authority;
import com.demo.demoforum.domain.question.entity.Question;
import com.demo.demoforum.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_username", columnNames = {"username"}),
        @UniqueConstraint(name = "uk_email", columnNames = {"email"})
})
public class Member extends BaseEntity {
    @OneToMany(mappedBy = "author")
    private final List<Question> questions = new ArrayList<>();
    @OneToMany(mappedBy = "author")
    private final List<Answer> answers = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id_generator")
    @SequenceGenerator(name = "member_id_generator", sequenceName = "member_id_sequence")
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;
    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name")}
    )
    private Set<Authority> authorities = new java.util.LinkedHashSet<>();

    public void addAuthority(Authority authority) {
        getAuthorities().add(authority);
    }

    public void removeAuthority(Authority authority) {
        getAuthorities().remove(authority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return id != null && Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "createdBy = " + createdBy + ", " +
                "lastModifiedBy = " + lastModifiedBy + ", " +
                "createdDate = " + createdDate + ", " +
                "lastModifiedDate = " + lastModifiedDate + ")";
    }
}