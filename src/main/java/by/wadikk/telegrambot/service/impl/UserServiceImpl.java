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
    public Optional<User> findByUserId(long id) {
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
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void addAnswer(Long id, Boolean isCorrect) {
        User newUser = userRepository.findById(id)
                .map(user -> User.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .isAdmin(user.getIsAdmin())
                        .totalCorrectAnswers(user.getTotalCorrectAnswers() + isCorrect.compareTo(false))
                        .totalAnswers(user.getTotalAnswers() + 1)
                        .build())
                .orElseThrow();
        userRepository.save(newUser);
    }
}
