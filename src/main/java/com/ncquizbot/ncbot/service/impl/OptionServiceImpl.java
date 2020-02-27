package com.ncquizbot.ncbot.service.impl;

import com.ncquizbot.ncbot.model.Option;
import com.ncquizbot.ncbot.model.Question;
import com.ncquizbot.ncbot.repo.OptionRepository;
import com.ncquizbot.ncbot.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {
    @Autowired
    private OptionRepository optionRepository;

    @Override
    public Option findOptionById(Integer id) {
        return optionRepository.findOptionById(id);
    }

    @Override
    public List<Option> findOptionByQuestion(Question question) {
        return optionRepository.findOptionByQuestion(question);
    }

    @Override
    public void createOptionsByQuestionAndContent(Question question, List<String> contents) {
        for (String content: contents) {
            Option option = new Option();
            option.setQuestion(question);
            option.setContent(content);
            optionRepository.save(option);
        }
    }

    @Override
    public Integer getCorrectIndexOfOptionByAnswer(String answerStr) {
        return null;
    }

}
