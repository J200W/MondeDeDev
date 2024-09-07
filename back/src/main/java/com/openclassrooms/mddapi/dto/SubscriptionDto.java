package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    @Getter
    @Setter
    private UserNoRoleDto user;

    @Getter
    @Setter
    private TopicDto topic;
}
