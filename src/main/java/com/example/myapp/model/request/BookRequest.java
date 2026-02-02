package com.example.myapp.model.request;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    String title;
    String imageUrl;
    BigDecimal price;
    String description;
    String publisher;
    Integer publicationYear;
    String dimensions;

    Long categoryId;
    Long authorId;

}
