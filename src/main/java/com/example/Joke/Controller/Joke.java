package com.example.Joke.Controller;

import com.example.Joke.model.DBJokes;
import com.example.Joke.service.JokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jokes")
public class Joke {

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

    public Long getNumber() {
        return service.getNumberid();
    }
}
