package com.example.backend.service.impl;

import com.example.backend.models.questions_answers.Answer;
import com.example.backend.models.questions_answers.Question;
import com.example.backend.payload.request.question_answer.AnswerRequest;
import com.example.backend.payload.request.question_answer.QuestionRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.AnswerRepository;
import com.example.backend.repository.QuestionRepository;
import com.example.backend.service.QuestionService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Autowired
    private EntityManager entityManager;
    @Override
    @Transactional
    public ResponseEntity<?> addQuestion(QuestionRequest questionRequest) {
        Question question = new Question();
        if (questionRequest.getLabel() == null || questionRequest.getRightAnswerId() == null || questionRequest.getAnswerRequest() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        question.setLabel(questionRequest.getLabel());
        List<Answer> answerSet = new ArrayList<>();
        List<AnswerRequest> answerRequestList = questionRequest.getAnswerRequest();
        answerRequestList.stream().forEach(index -> {
            Answer answer = new Answer(question, index.getLabel());
            answerSet.add(answer);
        });
        question.setAnswerList(answerSet);
        question.setRightAnswer(answerSet.get(questionRequest.getRightAnswerId()));
        questionRepository.save(question);
        Session session = entityManager.unwrap(Session.class);
        String query = "INSERT INTO answer VALUES ( ?, ?, ? )";
        session.doWork(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(query);
            for (Answer answer : answerSet) {
                stmt.setLong(1, answer.getId());
                stmt.setString(2, answer.getLabel());
                stmt.setLong(3, answer.getQuestion().getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.close();
        });
        session.close();

        return ResponseEntity.ok(new MessageResponse("Question has just been added successfully! "));
    }

}
