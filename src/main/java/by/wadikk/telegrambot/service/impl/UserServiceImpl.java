package by.wadikk.telegrambot.service.impl;

import by.wadikk.telegrambot.entity.User;
import by.wadikk.telegrambot.repository.UserRepository;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUserId(Long id) {
        return userRepository.findById(id);
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
    public void save(User user) {
        userRepository.save(user);

    }

    @Override
    public boolean isExist(Long id) {
        return findByUserId(id).isPresent();
    }
}
