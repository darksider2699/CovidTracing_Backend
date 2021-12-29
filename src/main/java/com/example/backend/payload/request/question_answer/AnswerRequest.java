package com.example.backend.payload.request.question_answer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequest {
    String label;

    public AnswerRequest(String label) {
        this.label = label;
    }

    public AnswerRequest() {
    }
}
