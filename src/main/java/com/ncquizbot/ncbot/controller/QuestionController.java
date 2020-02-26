package com.ncquizbot.ncbot.controller;

import com.ncquizbot.ncbot.model.Question;
import com.ncquizbot.ncbot.pojo.QuestionAndOptions;
import com.ncquizbot.ncbot.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private QuestionService questionService;

    @GetMapping(value = {"/all"})
    public String getQuestions(Model model) {
        System.out.println("getQuestions");
        List<Question> questions = questionService.findAll();
        model.addAttribute("questions", questions);
        return "questions";
    }

    @GetMapping(value = {"/add"})
    public String getAddQuestion(Model model) {
        System.out.println("getAddQuestion");
        if (Objects.isNull(model.getAttribute("questionAndOption"))) {
            QuestionAndOptions questionAndOptions = new QuestionAndOptions();
            model.addAttribute("questionAndOption", questionAndOptions);
            model.addAttribute("buttonValue", "createQuestion");
        }
        return "add_question";
    }

    @PostMapping(value = "/add", params = {"action"})
    public String postAddQuestion(@RequestParam(value = "answer_index", required = false) Integer answerIndex, @RequestParam("action") String action, ModelMap model, @ModelAttribute("questionAndOptions") QuestionAndOptions questionAndOptions) {
        if (Objects.isNull(questionAndOptions.getOptions())) {
            List<String> options = new ArrayList<>();
            questionAndOptions.setOptions(options);
        }
        if (action.equals("createQuestion")) {
            questionAndOptions.getOptions().removeAll(questionAndOptions.getOptions().stream()
                    .filter(StringUtils::isEmpty)
                    .collect(Collectors.toList()));
            questionService.createQuestionWithOptionsAndAnswer(questionAndOptions, answerIndex);
            return "redirect:/questions";
        }
        if (action.equals("addOption")) {
            System.out.println("Egor");
            List<String> options = new ArrayList<>();
            options.addAll(questionAndOptions.getOptions());
            options.add("");
            questionAndOptions.setOptions(options);
            model.addAttribute("questionAndOption", questionAndOptions);
            model.addAttribute("buttonValue", "editQuestion");
            model.addAttribute("buttonName", "edit question");
            System.out.println(questionAndOptions.getOptions());
            return "add_question";
        } else {
            model.addAttribute("questionAndOption", questionAndOptions);
            return "add_question";
        }

    }

    @PostMapping(value = "/add", params = {"removeOption"})
    public String revomeOptionToQuestion(Model model, @RequestParam("removeOption") Integer optionId, final QuestionAndOptions questionAndOptions, HttpServletRequest request) {
        LOGGER.info("QuestionController: revomeOptionToQuestion [START]");
        List<String> options = questionAndOptions.getOptions();
        options.remove(options.get(optionId));
        System.out.println("EGORKA-POMIDORKA");
        System.out.println(optionId);
        System.out.println(options);
        questionAndOptions.setOptions(options);
        model.addAttribute("questionAndOption", questionAndOptions);
        LOGGER.info("QuestionController: revomeOptionToQuestion [FINISH]");
        return "add_question";
    }

    @GetMapping(value = "/edit")
    public String getEditQuestion(Model model, @RequestParam("id") Integer questionId) {
        Question question = questionService.findQuestionById(questionId);
        QuestionAndOptions questionAndOptions = new QuestionAndOptions();
        questionAndOptions.setContent(question.getContent());
        List<String> optionsStr = question.getOptions().stream()
                .map(option -> option.getContent())
                .collect(Collectors.toList());
        questionAndOptions.setOptions(optionsStr);
        model.addAttribute("questionAndOption", questionAndOptions);
        model.addAttribute("buttonName", "edit question");
        model.addAttribute("buttonValue", "editQuestion");
        return "add_question";
    }

    @PostMapping(value = "/edit")
    public String postEditQuestion(Model model, @RequestParam("id") Integer questionId, @ModelAttribute("questionAndOption") QuestionAndOptions questionAndOptions) {
        questionService.editQuestionWithOptions(questionAndOptions, questionId);
        return "questions";
    }

    @GetMapping(value = "/remove")
    public String editQuestion(Model model, @RequestParam("id") Integer questionId) {
        questionService.deleteQuestionById(questionId);
        return "questions";
    }
}
