package by.wadikk.telegrambot.service;

import by.wadikk.telegrambot.entity.User;

import java.util.List;

public interface UserService {

    User findByUserId(long id);

    List<User> findAllUsers();

    void removeUser(User user);

    User save(User user);
}
