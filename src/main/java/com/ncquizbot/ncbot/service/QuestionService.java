package com.ncquizbot.ncbot.service;

import com.ncquizbot.ncbot.model.Question;
import com.ncquizbot.ncbot.pojo.QuestionAndAnswer;

import java.util.List;

public interface QuestionService {
    Question findQuestionById(Integer id);
    List<Question> findAll();
    void save(Question question);
    void delete(Question question);
    void createQuestionWithAnswers(QuestionAndAnswer questionAndAnswer);
    Question getNextQuestion(Integer currentQuestionId);
    Question findFirstQuestion();
}
