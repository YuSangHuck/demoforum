package com.demo.demoforum.feature.answer;

import com.demo.demoforum.entity.BaseEntity;
import com.demo.demoforum.feature.question.Question;
import com.demo.demoforum.feature.member.Member;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
//FIXME setter 제거
@Setter
public class Answer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_id_generator")
    @SequenceGenerator(name = "answer_id_generator", sequenceName = "answer_id_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false, foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_answer_to_author"))
    private Member author;

    //    FIXME 다대다 => 일대다,다대일
//    FIXME vote의 type으로 supertype(부모), subtype(자식) 구분지을것
    @ManyToMany
    @JoinTable(name = "answer_voter",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "voter_id"))
    private final Set<Member> voter = new LinkedHashSet<>();

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
                "Content = " + content + ")";
    }
}
