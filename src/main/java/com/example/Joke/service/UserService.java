package com.example.Joke.service;

import com.example.Joke.exceptions.UsernameAlreadyExistsException;
import com.example.Joke.model.User;
import com.example.Joke.model.UserAuthority;
import com.example.Joke.model.UserRole;
import com.example.Joke.repository.UserRepository;
import com.example.Joke.repository.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface, UserDetailsService {

    private final UserRolesRepository userRolesRepository;
    private final UserRepository userRepository;

    @Override
    public void registration(String username, String password) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = userRepository.save(
                    new User()
                            .setId(null)
                            .setUsername(username)
                            .setPassword(password)
                            .setLocked(false)
                            .setExpired(false)
                            .setEnabled(true));
            userRolesRepository.save(new UserRole(null, UserAuthority.USER, user));
        } else {
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
