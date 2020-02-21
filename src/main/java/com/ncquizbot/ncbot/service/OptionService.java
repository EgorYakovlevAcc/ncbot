package com.ncquizbot.ncbot.service;

import com.ncquizbot.ncbot.model.Option;
import com.ncquizbot.ncbot.model.Question;

import java.util.List;

public interface OptionService {
    Option findOptionById(Integer id);
    List<Option> findOptionByQuestion(Question question);
    void createOptionsByQuestionAndContent(Question question, List<String> contents);
}
