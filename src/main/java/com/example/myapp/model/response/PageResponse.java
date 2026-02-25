// package com.example.myapp.model.response;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
// public interface PageResponse extends JpaRepository<PageResponse, Long> {
// Page<PageResponse> findAll(Pageable pageable);

// boolean existsByTitle(String title);

// }
package com.example.myapp.model.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private List<T> data;

}