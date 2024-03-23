package com.example.Joke.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "joke_call")
public class UserCall {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jokes_call_id_seq")
    @SequenceGenerator(name = "jokes_call_id_seq", sequenceName = "jokes_call_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "joke_id")
    private Long jokeId;

    @Column(name = "call_time")
    private LocalDateTime callTime;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "joke_text")
    private String jokeText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "joke_id", insertable = false, updatable = false)
    private DBJokes dbJokes;
}
