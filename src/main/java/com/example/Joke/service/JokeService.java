package com.example.Joke.service;

import com.example.Joke.model.DBJokes;
import com.example.Joke.repository.JokesRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JokeService implements JokeServiceIterface{

    private final JokesRepositoryInterface jokes;

    @Override
    public void addjoke(DBJokes joke){
        joke.setCreated(new Date());
        jokes.save(joke);

    }

    @Override
    public List<DBJokes> getAllJokes(){
        return jokes.findAll();
    }
    @Override
    public Optional<DBJokes> getJokeById(Long id){
        return jokes.findById(id);
    }
    @Override
    public ResponseEntity<String> deleteJokeById(Long id){
        if (jokes.findById(id).isPresent()){
            jokes.deleteById(id);
            return ResponseEntity.ok("Шутка успешно удалена с id: "+id);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @Override
    public Optional<DBJokes> editJokeById(Long id, String text) {
        Optional<DBJokes> optionalJoke = jokes.findById(id);
        if (optionalJoke.isPresent()) {
            DBJokes joke = optionalJoke.get();
            joke.setText(text);
            joke.setUpdated(new Date());
            jokes.save(joke);
        }
        return optionalJoke;
    }

    public List<DBJokes> getTop5Jokes() {
        return jokes.findFirst5ByOrderByCreatedAsc();

    }

    public Page<DBJokes> pagejoke(Pageable pageable) {
        return jokes.findAll(pageable);
    }

    public DBJokes getRandomJokeDB() {
        return jokes.getRandomJoke();
    }

    public long getNumberid() {
        return jokes.count();
    }
}
