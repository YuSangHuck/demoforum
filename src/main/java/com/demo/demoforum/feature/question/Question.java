package com.demo.demoforum.feature.question;

import com.demo.demoforum.entity.BaseEntity;
import com.demo.demoforum.feature.answer.Answer;
import com.demo.demoforum.feature.user.SiteUser;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
//FIXME setter 제거
@Setter
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_id_generator")
    @SequenceGenerator(name = "question_id_generator", sequenceName = "question_id_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 40)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    //    demoForum\src\main\java\com\demo\demoforum\feature\question\Question.java:35:
//    warning: @Builder will ignore the initializing expression entirely.
//    If you want the initializing expression to serve as default, add @Builder.Default.
//    If it is not supposed to be settable during building, make the field final.
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private final List<Answer> answers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_question_to_author"))
    private SiteUser author;

    //    FIXME 다대다 => 일대다,다대일
//    FIXME vote의 type으로 supertype(부모), subtype(자식) 구분지을것
    @ManyToMany
    @JoinTable(name = "question_voter",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "voter_id"))
    private Set<SiteUser> voter = new LinkedHashSet<>();

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
                "id = " + id + ", " +
                "createdBy = " + createdBy + ", " +
                "lastModifiedBy = " + lastModifiedBy + ", " +
                "createdDate = " + createdDate + ", " +
                "lastModifiedDate = " + lastModifiedDate + ", " +
                "subject = " + subject + ", " +
                "content = " + content + ")";
    }
}
