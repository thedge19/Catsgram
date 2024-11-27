package ru.yandex.practicum.catsgram.model;

import lombok.*;

@Data
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Image {
   private Long id;
   private Long postId;
   private String originalFileName;
   private String filePath;
}
