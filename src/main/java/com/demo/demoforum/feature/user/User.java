package com.demo.demoforum.feature.user;

import com.demo.demoforum.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_username",columnNames = {"username"}),
        @UniqueConstraint(name = "uk_email",columnNames = {"email"})
})
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "user_id_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
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
