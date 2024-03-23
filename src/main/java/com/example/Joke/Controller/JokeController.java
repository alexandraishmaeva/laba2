package com.example.Joke.Controller;

import com.example.Joke.model.DBJokes;
import com.example.Joke.service.JokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jokes")
public class JokeController {

    private final JokeService service;

    @PostMapping
    ResponseEntity<Void> addjoke(@RequestBody DBJokes text) {
        service.addjoke(text);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<List<DBJokes>> showAllJokes() {
        return ResponseEntity.ok(service.getAllJokes());
    }

    @GetMapping("/{id}")
    ResponseEntity<DBJokes> showjokebyid(@PathVariable Long id) {
        return service.getJokeById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deletejokebyid(@PathVariable Long id) {
        return service.deleteJokeById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<DBJokes> editjokebyid(@PathVariable Long id, @RequestBody DBJokes text) {
        return service.editJokeById(id, text.getText()).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/top5")
    List<DBJokes> getTop5Jokes() {
        return service.getTop5Jokes();
    }

    @GetMapping("/pages/{page}")
    public Page<DBJokes> jokesPage(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 2); // Задаем размер страницы по умолчанию
        return service.pagejoke(pageable);
    }

    @GetMapping("/random")
    public DBJokes getRandomJokeDB() {
        return service.getRandomJokeDB();
    }

    public Long getNumber() {
        return service.getNumberid();
    }
}
