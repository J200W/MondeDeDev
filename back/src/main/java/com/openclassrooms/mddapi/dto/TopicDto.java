package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {
    
    @Getter
    @Setter
    public String title;

    @Getter
    @Setter
    public String description;
}
