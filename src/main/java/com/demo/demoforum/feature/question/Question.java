package com.demo.demoforum.feature.question;

import com.demo.demoforum.entity.BaseEntity;
import com.demo.demoforum.feature.answer.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_id_generator")
    @SequenceGenerator(name = "question_id_generator", sequenceName = "question_id_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 40)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

//    demoForum\src\main\java\com\demo\demoforum\feature\question\Question.java:35:
//    warning: @Builder will ignore the initializing expression entirely.
//    If you want the initializing expression to serve as default, add @Builder.Default.
//    If it is not supposed to be settable during building, make the field final.
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private final List<Answer> answers = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Question question = (Question) o;
        return id != null && Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "createdBy = " + createdBy + ", " +
                "lastModifiedBy = " + lastModifiedBy + ", " +
                "createdDate = " + createdDate + ", " +
                "lastModifiedDate = " + lastModifiedDate + ", " +
                "subject = " + getSubject() + ", " +
                "content = " + getContent() + ")";
    }
}
