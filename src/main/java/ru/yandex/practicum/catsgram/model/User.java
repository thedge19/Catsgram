package ru.yandex.practicum.catsgram.model;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
    private String userName;
    private Long id;
    private String email;
    private String password;
    private Instant registrationDate;
}
