package com.example.backend.service.impl;

import com.example.backend.models.questions_answers.Answer;
import com.example.backend.models.questions_answers.Question;
import com.example.backend.payload.request.question_answer.QuestionRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.AnswerRepository;
import com.example.backend.repository.QuestionRepository;
import com.example.backend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Override
    public ResponseEntity findAll() {
        return new ResponseEntity<>(
                questionRepository.findAll(), HttpStatus.OK);
    }
    @Override
    public ResponseEntity findById(Long id) {
        return new ResponseEntity<>(
                questionRepository.findById(id), HttpStatus.OK);
    }
    @Override
    @Transactional
    public ResponseEntity<?> addQuestion(QuestionRequest questionRequest) {
        Question question = new Question();
        if (questionRequest.getLabel() == null || questionRequest.getRightAnswerPosition() == null || questionRequest.getAnswerRequest() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        question.setLabel(questionRequest.getLabel());
        List<Answer> answerSet = questionRequest.getAnswerRequest().stream().map(index -> new Answer(question, index.getLabel())).collect(Collectors.toList());
        question.setAnswerList(answerSet);
        question.setRightAnswer(answerSet.get(questionRequest.getRightAnswerPosition()));
        questionRepository.save(question);

        return ResponseEntity.ok(new MessageResponse("Question has just been added successfully! "));
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateQuestion(QuestionRequest updateAnswerOfQuestionRequest, Long id) {
        Optional<Question> optQues = questionRepository.findById(id);
        if (!optQues.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Question question = optQues.get();
        questionRepository.delete(question);
        addQuestion(new QuestionRequest(updateAnswerOfQuestionRequest.getLabel(), updateAnswerOfQuestionRequest.getAnswerRequest(), updateAnswerOfQuestionRequest.getRightAnswerPosition()));
        return ResponseEntity.ok(new MessageResponse("Question has just been updated successfully! "));
    }


}
