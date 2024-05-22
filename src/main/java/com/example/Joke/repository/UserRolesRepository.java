package com.example.Joke.repository;


import com.example.Joke.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRolesRepository extends CrudRepository<UserRole, Long> {
}
