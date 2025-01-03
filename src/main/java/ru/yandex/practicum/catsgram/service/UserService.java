package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Указываем, что класс UserService - является бином и его
// нужно добавить в контекст приложения
@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();


    public Collection<User> findAll() {
        return users.values();
    }

    public User findUserById(Long userId) {
        return findAll()
                .stream()
                .filter(p -> Objects.equals(p.getId(), userId))
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пользователь № %d не найден", userId)));
    }

    public User create(User user) {
        if (user.getEmail() == null) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        for (User existingUser : users.values()) {
            if (existingUser.getEmail().equals(user.getEmail())) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
        }
        // формируем дополнительные данные
        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        // сохраняем новую публикацию в памяти приложения
        users.put(user.getId(), user);
        return user;
    }

    public User update(User newUser) {
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            for (User existingUser : users.values()) {
                if (!Objects.equals(newUser.getId(), oldUser.getId()) && Objects.equals(newUser.getEmail(), existingUser.getEmail())) {
                    throw new ConditionsNotMetException("Описание не может быть пустым");
                }
            }
            // если публикация найдена и все условия соблюдены, обновляем её содержимое
            if (newUser.getUsername() != null) {
                oldUser.setUsername(newUser.getUsername());
            }
            if (newUser.getPassword() != null) {
                oldUser.setPassword(newUser.getPassword());
            }
            if (newUser.getEmail() != null) {
                oldUser.setEmail(newUser.getEmail());
            }
            return oldUser;
        }
        throw new NotFoundException("Пост с id = " + newUser.getId() + " не найден");
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
