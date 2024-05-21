package com.example.Joke;

import com.example.Joke.repository.JokesRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.example.Joke.config.BotConfig;

@SpringBootApplication
public class JokeApplication {

	public static void main(String[] args) {
		SpringApplication.run(JokeApplication.class, args);

	}
}
