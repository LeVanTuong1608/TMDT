package com.example.myapp.util;

import org.springframework.stereotype.Component;

import com.example.myapp.entity.Book;
import com.example.myapp.model.response.BookResponse;

@Component
public class BookMapper {

    // private BookMapper() {
    // // utility class
    // }

    public BookResponse toResponse(Book book) {

        return BookResponse.builder()
                // .bookId(book.getId())
                .id(book.getId())
                .title(book.getTitle())
                .imageUrl(book.getImageUrl())
                .price(book.getPrice())
                .description(book.getDescription())
                .publisher(book.getPublisher())
                .publicationYear(book.getPublicationYear())
                .dimensions(book.getDimensions())

                // flatten object
                // .categoryId(book.getCategory().getId())
                .categoryName(book.getCategory().getCategoryName())

                // .authorId(book.getAuthor().getId())
                .authorName(book.getAuthor().getAuthorName())
                .build();
    }
}
