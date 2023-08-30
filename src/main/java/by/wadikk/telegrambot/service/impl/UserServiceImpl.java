package by.wadikk.telegrambot.service.impl;

import by.wadikk.telegrambot.entity.User;
import by.wadikk.telegrambot.repository.UserRepository;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUserId(long id) {
        //todo Optional
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void removeUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}