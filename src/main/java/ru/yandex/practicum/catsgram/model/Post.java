package ru.yandex.practicum.catsgram.model;

import lombok.*;

import java.time.Instant;

@Data
public class Post {
    private Long id;
    private Long authorId;
    private String description;
    private Instant postDate;
}
