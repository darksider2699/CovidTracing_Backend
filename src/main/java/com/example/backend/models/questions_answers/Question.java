package com.example.backend.models.questions_answers;

import com.example.backend.models.department.Department;
import com.example.backend.models.questions_answers.Answer;
import com.example.backend.models.user.MedicalUserInformation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {})}
)
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> answerList;

    @OneToOne(cascade = CascadeType.ALL)
    private Answer rightAnswer;


    private String label;

    public Question() {
    }

    public Question(List<Answer> answerList, Answer rightAnswer, String label) {
        this.answerList = answerList;
        this.label = label;
        this.rightAnswer = rightAnswer;
    }
}
