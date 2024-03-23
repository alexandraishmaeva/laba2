package com.example.Joke;

import lombok.Data;
import org.json.JSONException;
import com.example.Joke.config.BotConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.json.JSONArray;
import org.json.JSONObject;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
@Data
@PropertySource("application.properties")
public class MyAmazingBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String token;


    public String processJson(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        Random random = new Random();
// Генерируем случайный индекс
        int randomIndex = random.nextInt(jsonArray.length());
        JSONObject jsonObject = jsonArray.getJSONObject(randomIndex);
        String text = jsonObject.getString("text");

        return text;
    }
    @Override
    public void onUpdateReceived(Update update) {
// We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            if ("/jokes".equals(messageText)) {
                String joke = getJokeFromExternalService();
                String jokes = null;; // Обрабатываем JSON и получаем список текстов
                try {
                    jokes = processJson(joke);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                sendTextMessage(chatId, jokes);// Отправляем шутку пользователю

            }
        }
    }

    private String getJokeFromExternalService() {
        // Выполняем HTTP-запрос к localhost:8080, чтобы получить шутку
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8080/jokes", String.class);
    }

    private void sendTextMessage(String chatId, String text) {
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return getBotName();
    }

    @Override
    public String getBotToken(){
        return getToken();
    }
}
