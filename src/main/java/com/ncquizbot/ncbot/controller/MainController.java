package com.ncquizbot.ncbot.controller;

import com.ncquizbot.ncbot.model.Question;
import com.ncquizbot.ncbot.model.User;
import com.ncquizbot.ncbot.pojo.QuestionAndOptions;
import com.ncquizbot.ncbot.service.AnswerService;
import com.ncquizbot.ncbot.service.OptionService;
import com.ncquizbot.ncbot.service.QuestionService;
import com.ncquizbot.ncbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private AnswerService answerService;

    @GetMapping("/users")
    @ResponseBody
    public List<User> getShowUsers(Model model) {
        System.out.println("EGORKA-POMIDORKA-DORKA");
       return userService.findAll();
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
        if (Objects.isNull(model.getAttribute("questionAndOption"))) {
            System.out.println("EGORKA");
            QuestionAndOptions questionAndOptions = new QuestionAndOptions();
            model.addAttribute("questionAndOption", questionAndOptions);
            model.addAttribute("buttonValue", "createQuestion");
        }
        return "add_question";
    }
}
