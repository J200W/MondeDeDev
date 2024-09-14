package com.openclassrooms.mddapi.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.UserNoRoleDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@AllArgsConstructor
@Data
/**
 * La classe ModelMapperService est le service pour le modelMapper.
 * @see IModelMapperService
 */
public class ModelMapperService implements IModelMapperService{

    private final ModelMapper modelMapper;

    public ModelMapperService() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public UserDto convertUserToUserDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRole(user.getRole().stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toList()));
        return userDto;
    }

    @Override
    public List<SubscriptionDto> convertSubsToSubDtos(List<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(subscription -> {
                    SubscriptionDto subscriptionDto = modelMapper.map(subscription, SubscriptionDto.class);
                    subscriptionDto.setUser(modelMapper.map(subscription.getUser(), UserNoRoleDto.class));
                    subscriptionDto.setTopic(modelMapper.map(subscription.getTopic(), TopicDto.class));
                    return subscriptionDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TopicDto> convertTopicsToTopicDtos(List<Topic> topics) {
        return topics.stream()
                .map(topic -> modelMapper.map(topic, TopicDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> convertPostsToPostDto(List<Post> topics) {
        return topics.stream()
                .map(topic -> modelMapper.map(topic, PostDto.class))
                .collect(Collectors.toList());
    }
}
