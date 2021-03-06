package com.example.backend.payload.request.question_answer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionRequest {
    String label;
    List<AnswerRequest> answerRequest;
    Integer rightAnswerPosition;

    public QuestionRequest(String label, List<AnswerRequest> answerRequest, Integer rightAnswerId) {
        this.label = label;
        this.answerRequest = answerRequest;
        this.rightAnswerPosition = rightAnswerId;
    }

    public QuestionRequest() {
    }
}
