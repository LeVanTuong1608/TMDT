package com.example.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.entity.*;
import java.util.*;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndBook(Cart cart, Book book);

    List<CartItem> findByCart(Cart cart);
}
