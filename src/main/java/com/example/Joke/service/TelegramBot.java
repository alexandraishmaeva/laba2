package com.example.Joke.service;

import com.example.Joke.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Random;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config){
        this.config = config;
    }

    public String processJson(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        Random random = new Random();
// Генерируем случайный индекс
        int randomIndex = random.nextInt(jsonArray.length());
        JSONObject jsonObject = jsonArray.getJSONObject(randomIndex);
        String text = jsonObject.getString("text");

        return text;
    }

    public String GetRandomJoke(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        Random random = new Random();
        int randomIndex = random.nextInt(jsonArray.length());
        JSONObject jsonObject = jsonArray.getJSONObject(randomIndex);
        String text = jsonObject.getString("text");

        return text;
    }

    private String getJokeFromHttp() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8080/jokes", String.class);
    }

    @Override
    public void onUpdateReceived(Update update) {
// We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            if ("/jokes".equals(messageText)) {
                String joke = getJokeFromExternalService();
                String jokes = null;
                // Обрабатываем JSON и получаем список текстов
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
        return config.getBotName();
    }

    @Override
    public String getBotToken(){
        return config.getToken();
    }
}