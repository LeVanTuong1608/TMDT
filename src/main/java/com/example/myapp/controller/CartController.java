// package com.example.myapp.controller;

// import java.util.List;

// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.myapp.service.*;

// @RestController
// @RequestMapping("/api/cart")
// @PreAuthorize("hasRole('USER')")
// public class CartController {

//     private final CartService cartService;

//     /* ================== ADD TO CART ================== */
//     // POST /api/cart/add?bookId=1&quantity=2
//     @PostMapping("/add")
//     public ResponseEntity<Void> addToCart(
//             @RequestParam Long bookId,
//             @RequestParam int quantity) {

//         cartService.addToCart(bookId, quantity);
//         return ResponseEntity.ok().build();
//     }

//     /* ================== REMOVE ITEM ================== */
//     // DELETE /api/cart/remove?bookId=1
//     @DeleteMapping("/remove")
//     public ResponseEntity<Void> removeFromCart(
//             @RequestParam Long bookId) {

//         cartService.removeFromCart(bookId);
//         return ResponseEntity.ok().build();
//     }

//     /* ================== VIEW MY CART ================== */
//     // GET /api/cart
//     @GetMapping
//     public ResponseEntity<CartResponse> getMyCart() {
//         return ResponseEntity.ok(cartService.getMyCart());
//     }
// }
package com.example.myapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.myapp.model.response.CartResponse;
import com.example.myapp.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class CartController {

    private final CartService cartService;

    /* ================== ADD TO CART ================== */
    // POST /api/cart/add?bookId=1&quantity=2
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(
            @RequestParam Long bookId,
            @RequestParam int quantity) {

        cartService.addToCart(bookId, quantity);
        return ResponseEntity.ok().build();
    }

    /* ================== REMOVE ITEM ================== */
    // DELETE /api/cart/remove?bookId=1
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromCart(
            @RequestParam Long bookId) {

        cartService.removeFromCart(bookId);
        return ResponseEntity.ok().build();
    }

    /* ================== VIEW MY CART ================== */
    // GET /api/cart
    @GetMapping
    public ResponseEntity<CartResponse> getMyCart() {
        return ResponseEntity.ok(cartService.getMyCart());
    }
}
