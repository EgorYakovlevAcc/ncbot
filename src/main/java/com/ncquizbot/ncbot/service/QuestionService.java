package com.ncquizbot.ncbot.service;

import com.ncquizbot.ncbot.model.Question;
import com.ncquizbot.ncbot.pojo.QuestionAndOptions;

import java.util.List;

public interface QuestionService {
    Question findQuestionById(Integer id);
    List<Question> findAll();
    void save(Question question);
    void delete(Question question);
    void createQuestionWithOptionsAndAnswer(QuestionAndOptions questionAndOptions, Integer answerId);
    Question getNextQuestion(Integer currentQuestionId);
    Question findFirstQuestion();
    void deleteQuestionById(Integer id);

    void editQuestionWithOptions(QuestionAndOptions questionAndOptions, Integer questionId);
}
