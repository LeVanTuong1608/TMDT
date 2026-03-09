package com.example.myapp.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryRequest {

    // private Long id;
    private String categoryName;


}
