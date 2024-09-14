package com.openclassrooms.mddapi.utils;


import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;

import java.util.List;

public interface IModelMapperService {
    UserDto convertUserToUserDto(User user);
    List<SubscriptionDto> convertSubsToSubDtos(List<Subscription> subscriptions);

    List<TopicDto> convertTopicsToTopicDtos(List<Topic> topics);

    List<PostDto> convertPostsToPostDto(List<Post> topics);
}
