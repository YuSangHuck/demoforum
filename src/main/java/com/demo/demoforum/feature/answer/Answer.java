package com.demo.demoforum.feature.answer;

import com.demo.demoforum.entity.BaseEntity;
import com.demo.demoforum.feature.question.Question;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Answer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_id_generator")
    @SequenceGenerator(name = "answer_id_generator", sequenceName = "answer_id_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String Content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Answer answer = (Answer) o;
        return id != null && Objects.equals(id, answer.id);
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
                "lastModifiedDate = " + lastModifiedDate + ", " +
                "Content = " + Content + ")";
    }
}
