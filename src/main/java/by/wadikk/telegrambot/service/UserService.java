package by.wadikk.telegrambot.service;

import by.wadikk.telegrambot.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUserId(long id);

    List<User> findAllUsers();

    void removeUser(User user);

    User save(User user);

    void addAnswer(Long id, Boolean isCorrect);

}
