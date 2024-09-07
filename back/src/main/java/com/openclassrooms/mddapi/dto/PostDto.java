package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @Getter
    @Setter
    public String title;

    @Getter
    @Setter
    public String content;

    @Getter
    @Setter
    public LocalDateTime date = LocalDateTime.now();

    @Getter
    @Setter
    public UserDto user;

    @Getter
    @Setter
    public TopicDto topic;
}
