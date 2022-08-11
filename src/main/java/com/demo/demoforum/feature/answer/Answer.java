package com.demo.demoforum.feature.answer;

import com.demo.demoforum.entity.BaseEntity;
import com.demo.demoforum.feature.question.Question;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@ToString(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
public class Answer extends BaseEntity {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_id_generator")
    @SequenceGenerator(name = "answer_id_generator", sequenceName = "answer_id_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @ToString.Include
    @Column(columnDefinition = "TEXT")
    private String Content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
}
