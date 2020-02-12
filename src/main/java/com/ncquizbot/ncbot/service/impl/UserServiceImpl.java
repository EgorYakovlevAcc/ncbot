package com.ncquizbot.ncbot.service.impl;

import com.ncquizbot.ncbot.model.Question;
import com.ncquizbot.ncbot.model.User;
import com.ncquizbot.ncbot.repo.UserRepository;
import com.ncquizbot.ncbot.service.QuestionService;
import com.ncquizbot.ncbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionService questionService;

    @Override
    public User findUserById(Integer id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByChatId(Long chatId) {
        return userRepository.findUserByChatId(chatId);
    }

    @Override
    public User findUserByTelegramId(Integer telegramId) {
        return userRepository.findUserByTelegramId(telegramId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User createAndSaveUserByTelegramMessage(Message message) {
        org.telegram.telegrambots.api.objects.User telegramUser = message.getFrom();
        User u = userRepository.findUserByTelegramId(telegramUser.getId());
        if (Objects.isNull(u)) {
            User user = new User();
            user.setActiveNow(true);
            user.setTelegramId(telegramUser.getId());
            user.setFirstName(telegramUser.getFirstName());
            user.setLastName(telegramUser.getLastName());
            Chat chat = message.getChat();
            user.setChatId(chat.getId());
            user.setCurrentQuestionId(-1);
            user.setScore(0);
            userRepository.save(user);
            return user;
        } else {
            return u;

        }
    }

    @Override
    public boolean checkIsThisUserExists(User user) {
        return Objects.nonNull(userRepository.findUserByTelegramId(user.getTelegramId()));
    }

    @Override
    public boolean checkIsThisQuestionLast(User user) {
        return Objects.isNull(getNextQuestionForUser(user)) ? true : false;
    }

    public Question getNextQuestionForUser(User user) {
        Integer questionId = user.getCurrentQuestionId();
        if (Objects.nonNull(questionId)) {
            return questionService.getNextQuestion(questionId);
        }
        return null;
    }

    @Override
    public void increaseUserScore(User user) {
        Integer currentScore = user.getScore();
        user.setScore(currentScore + 1);
        userRepository.save(user);
    }

    @Override
    public void setNextQuestionToUser(User user) {
        Question question = getNextQuestionForUser(user);
        if (Objects.nonNull(question)) {
            user.setCurrentQuestionId(question.getId());
            System.out.println("EGORKA = " + question.getId());
            userRepository.save(user);
        }
    }

    @Override
    public void turnOffUserActivityStatus(User user) {
        user.setActiveNow(false);
        userRepository.save(user);
    }

    @Override
    public void setCurrentQuestionToUser(User user, Question question) {
        user.setCurrentQuestionId(question.getId());
        userRepository.save(user);
    }
}
