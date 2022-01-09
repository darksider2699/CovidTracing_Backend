package com.example.backend.payload.request.question_answer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateAnswerOfQuestionRequest {
    List<AnswerRequest> answerRequest;
    Integer rightAnswerPosition;

    public UpdateAnswerOfQuestionRequest(List<AnswerRequest> answerRequest, Integer rightAnswerId) {
        this.answerRequest = answerRequest;
        this.rightAnswerPosition = rightAnswerId;
    }

    public UpdateAnswerOfQuestionRequest() {
    }
}
