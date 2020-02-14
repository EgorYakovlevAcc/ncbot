package com.ncquizbot.ncbot.bot;

import com.ncquizbot.ncbot.model.Question;
import com.ncquizbot.ncbot.model.User;
import com.ncquizbot.ncbot.service.QuestionService;
import com.ncquizbot.ncbot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        String messageText = getOutputMessage(update);
        SendMessage answer = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId())
                .setText(messageText);
        try {
            execute(answer); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getOutputMessage(Update update) {
        Message message = update.getMessage();
        if (update.hasMessage() && message.hasText()) {
            String messageText = "";
            User user = userService.createAndSaveUserByTelegramMessage(message);
            Question currentQuestion = null;
            if (user.getCurrentQuestionId() == -1) {
                currentQuestion = questionService.findQuestionById(questionService.findFirstQuestion().getId());
                messageText = currentQuestion.getContent();
                userService.setCurrentQuestionToUser(user, currentQuestion);
//                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//                replyKeyboardMarkup.setOneTimeKeyboard(true);
//                List<KeyboardRow> keyboardRowList = new ArrayList<>();
//                KeyboardRow keyboardRow = new KeyboardRow();
//                keyboardRow.add("Egor");
//                keyboardRow.add("Pomidor");
//                keyboardRowList.add(keyboardRow);
//                replyKeyboardMarkup.setKeyboard(keyboardRowList);
            } else {
                currentQuestion = questionService.findQuestionById(user.getCurrentQuestionId());
                LOGGER.error("message.getText(): {}", message.getText());
                LOGGER.error("currentQuestion.getContent(): {}", currentQuestion.getAnswer().getContent());
                if (message.getText().equals(currentQuestion.getAnswer().getContent())) {
                    userService.increaseUserScore(user);
                }
                if (!userService.checkIsThisQuestionLast(user)) {
                    userService.setNextQuestionToUser(user);
                    messageText = questionService.findQuestionById(user.getCurrentQuestionId()).getContent();
                } else {
                    userService.turnOffUserActivityStatus(user);
                    userService.updateUserSessionEndDate(user);
//                    userService.delete(user);
                    messageText = "Thank you it was last question. Your score is " + user.getScore();
                }
            }
            userService.updateLastUserSessionDate(user);
            return messageText;
        }
        return null;
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "netcracker_quiz_bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "827026140:AAECQOwWUsWYkygsr89VNo0DeHWhWr_Lml4";
    }
}