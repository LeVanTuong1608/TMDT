package com.example.myapp.model.response;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    Long id;
    String title;
    String imageUrl;
    BigDecimal price;
    String description;
    String publisher;
    Integer publicationYear;
    String dimensions;

    Long categoryId;
    String categoryName;

    Long authorId;
    String authorName;
}
