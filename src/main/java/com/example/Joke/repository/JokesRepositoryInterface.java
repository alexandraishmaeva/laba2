package com.example.Joke.repository;

import com.example.Joke.model.DBJokes;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JokesRepositoryInterface extends JpaRepository<DBJokes, Long> {}
