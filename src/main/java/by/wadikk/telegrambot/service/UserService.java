package by.wadikk.telegrambot.service;

import by.wadikk.telegrambot.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUserId(Long id);

    List<User> findAllUsers();

    void removeUser(User user);

    void save(User user);

    boolean isExist(Long id);
}
