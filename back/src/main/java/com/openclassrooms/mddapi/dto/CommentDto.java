package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.openclassrooms.mddapi.models.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    
    @Getter
    @Setter
    private UserNoRoleDto user;

    @Getter
    @Setter
    private String content;

    public LocalDateTime date = LocalDateTime.now();

    public static List<CommentDto> convertToDto(List<Comment> comments, ModelMapper modelMapper) {
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }
}
