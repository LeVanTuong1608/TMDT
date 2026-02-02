package com.example.myapp.util;

import com.example.myapp.entity.Book;
import com.example.myapp.model.response.BookResponse;

public class BookMapper {

    private BookMapper() {
        // utility class
    }

    public static BookResponse toResponse(Book book) {

        if (book == null) {
            return null;
        }

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .imageUrl(book.getImageUrl())
                .price(book.getPrice())
                .description(book.getDescription())
                .publisher(book.getPublisher())
                .publicationYear(book.getPublicationYear())
                .dimensions(book.getDimensions())

                // flatten object
                .categoryId(book.getCategory().getId())
                .categoryName(book.getCategory().getCategoryName())

                .authorId(book.getAuthor().getId())
                .authorName(book.getAuthor().getAuthorName())
                .build();
    }
}
