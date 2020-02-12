package com.ncquizbot.ncbot.controller;

import com.ncquizbot.ncbot.model.Answer;
import com.ncquizbot.ncbot.model.Question;
import com.ncquizbot.ncbot.pojo.QuestionAndAnswer;
import com.ncquizbot.ncbot.service.AnswerService;
import com.ncquizbot.ncbot.service.QuestionService;
import com.ncquizbot.ncbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @GetMapping("/users")
    public String getShowUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping(value = {"/index", "/"})
    public String getIndex(Model model) {
        System.out.println("getIndex");
        return "index";
    }

    @GetMapping(value = {"questions"})
    public String getQuestions(Model model) {
        System.out.println("getQuestions");
        List<Question> questions = questionService.findAll();
        System.out.println(questions);
        model.addAttribute("questions", questions);
        return "questions";
    }

    @GetMapping(value = {"questions/add"})
    public String getAddQuestion(Model model) {
        System.out.println("getAddQuestion");
        QuestionAndAnswer questionAndAnswer = new QuestionAndAnswer();
        model.addAttribute("questionAndAnswer", questionAndAnswer);
        return "add_question";
    }

    @PostMapping("questions/add")
    public String postAddQuestion(@ModelAttribute("questionAndAnswer") QuestionAndAnswer questionAndAnswer) {
        System.out.println("postAddQuestion");
        questionService.createQuestionWithAnswers(questionAndAnswer);
        return "redirect:/questions";
    }

    @GetMapping("edit/question")
    public String getEntityEditorForQuestion(Model model, @RequestParam("id") Integer id) {
        Question question = questionService.findQuestionById(id);
        model.addAttribute("question", question);
        return "editor";
    }

    @GetMapping("edit/anwser")
    public String getEntityEditorForAnswer(Model model, @RequestParam("id") Integer id) {
                Answer answer = answerService.findAnswerById(id);
                model.addAttribute("answer", answer);
        return "editor";
    }
}
