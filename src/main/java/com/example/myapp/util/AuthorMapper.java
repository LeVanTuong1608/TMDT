package com.example.myapp.util;

import org.springframework.stereotype.Component;

import com.example.myapp.entity.Author;
import com.example.myapp.model.response.AuthorResponse;

@Component
public class AuthorMapper {
    public AuthorResponse toResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .birth(author.getBirth())
                // .biography(author.getBiography())
                // .imageUrl(author.getImageUrl())
                .build();
    }

}
