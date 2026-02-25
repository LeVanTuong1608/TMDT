package com.example.myapp.model.request;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthorRequest {
    private Long id;
    private String name;
    private LocalDate birth;
}
