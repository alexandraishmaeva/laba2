package com.example.Joke.repository;

import com.example.Joke.model.DBJokes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface JokesRepositoryInterface extends JpaRepository<DBJokes, Long> {
    List<DBJokes> findFirst5ByOrderByCreatedAsc();

    @Query(value = "SELECT * FROM jokes ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    DBJokes getRandomJoke();
}
