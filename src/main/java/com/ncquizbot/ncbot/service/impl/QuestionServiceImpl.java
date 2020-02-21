package com.ncquizbot.ncbot.service.impl;

import com.ncquizbot.ncbot.model.Option;
import com.ncquizbot.ncbot.model.Question;
import com.ncquizbot.ncbot.pojo.QuestionAndOptions;
import com.ncquizbot.ncbot.repo.QuestionRepository;
import com.ncquizbot.ncbot.service.AnswerService;
import com.ncquizbot.ncbot.service.OptionService;
import com.ncquizbot.ncbot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private OptionService optionService;

    @Override
    public Question findQuestionById(Integer id) {
        return questionRepository.findQuestionById(id);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    @Override
    public void createQuestionWithOptionsAndAnswer(QuestionAndOptions questionAndOptions, Integer answerId) {
        Question question = new Question();
        question.setContent(questionAndOptions.getContent());
        String contentOfAnswer = questionAndOptions.getOptions().get(answerId);
        questionRepository.save(question);
        optionService.createOptionsByQuestionAndContent(question, questionAndOptions.getOptions());
        answerService.createAnswersByContents(question, contentOfAnswer);
    }

    @Override
    public Question getNextQuestion(Integer currentQuestionId) {
        List<Question> questions = questionRepository.findAll();
        Integer currentQuestionIndex = questions.stream()
                .map(question -> question.getId())
                .collect(Collectors.toList())
                .indexOf(currentQuestionId);
        if ((currentQuestionIndex < questions.size() - 1) && (Objects.nonNull(currentQuestionId))) {
            Question question = questions.get(currentQuestionIndex + 1);
            return question;
        }
        else {
            return null;
        }
    }

    @Override
    public Question findFirstQuestion() {
        return questionRepository.findAll().stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteQuestionById(Integer id) {
        questionRepository.deleteById(id);
    }

    @Override
    public void editQuestionWithOptions(QuestionAndOptions questionAndOptions, Integer questionId) {
        Question question = questionRepository.findQuestionById(questionId);
        optionService.createOptionsByQuestionAndContent(question, questionAndOptions.getOptions());
    }

}
