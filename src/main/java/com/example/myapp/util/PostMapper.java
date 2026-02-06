package com.example.myapp.util;

import org.springframework.stereotype.Component;

import com.example.myapp.entity.Post;
import com.example.myapp.model.response.PostResponse;

@Component
public class PostMapper {

    public PostResponse toResponse(Post post) {

        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .slug(post.getSlug())
                .description(post.getDescription())
                .thumbnail(post.getThumbnail())
                .createdAt(post.getCreatedAt())
                .publishedAt(post.getPublishedAt())
                .createdBy(post.getCreatedBy() != null
                        ? post.getCreatedBy().getFullName()
                        : null)
                .build();
    }
}
