package com.example.backend.models.questions_answers;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {})}
)
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Question question;

    private String label;

    public Answer() {
    }

    public Answer(Question question, String label) {
        this.question = question;
        this.label = label;
    }
}
