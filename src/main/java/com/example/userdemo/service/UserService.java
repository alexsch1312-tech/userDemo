package com.example.userdemo.service;

import com.example.userdemo.repository.User;
import com.example.userdemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
    public User create(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("юзер с таким имейлом уже существует");
        }
        user.setAge(Period.between(user.getBirth(), LocalDate.now()).getYears());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw  new IllegalStateException("Юзера с id: " + id +" не существует");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, String email, String name) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw  new IllegalStateException("Юзера с id: " + id +" не существует");
        }
        User user = optionalUser.get();

        if (email != null && !email.equals(user.getEmail())) {
            Optional<User> foundByEmail = userRepository.findByEmail(email);
            if (foundByEmail .isPresent()) {
                throw new IllegalStateException("юзер с таким имейлом уже существует");
            }
            user.setEmail(email);
        }

        if (name != null && !name.equals(user.getName())) {
            user.setName(name);
        }
        // Не нужно есть есть @Transactional
        //userRepository.save(user);
    }
}

