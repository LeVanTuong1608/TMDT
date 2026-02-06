// package com.example.myapp.service.impl;

// import java.time.LocalDateTime;
// import java.util.List;

// import org.springframework.stereotype.Service;

// import com.example.myapp.service.*;

// import com.example.myapp.repository.*;
// import com.example.myapp.entity.*;
// import com.example.myapp.exception.*;
// import com.example.myapp.util.*;

// @Service
// public class CartServiceImpl implements CartService {

//     private final CartRepository cartRepository;
//     private final CartItemRepository cartItemRepository;
//     private final BookRepository bookRepository;

//     public CartServiceImpl(
//             CartRepository cartRepository,
//             CartItemRepository cartItemRepository,
//             BookRepository bookRepository) {
//         this.cartRepository = cartRepository;
//         this.cartItemRepository = cartItemRepository;
//         this.bookRepository = bookRepository;
//     }

//     @Override
//     public void addToCart(Long bookId, int quantity) {

//         if (quantity <= 0) {
//             throw new AppException(ErrorCode.INVALID_QUANTITY);
//         }

//         User user = SecurityUtil.getCurrentUser();

//         Cart cart = cartRepository.findByUserAndStatus(user, "active")
//                 .orElseGet(() -> cartRepository.save(
//                         new Cart(null, user, "active", LocalDateTime.now())));

//         Book book = bookRepository.findById(bookId)
//                 .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

//         CartItem item = cartItemRepository.findByCartAndBook(cart, book)
//                 .orElse(new CartItem(null, cart, book, 0, book.getPrice()));

//         item.setQuantity(item.getQuantity() + quantity);
//         cartItemRepository.save(item);
//     }

//     @Override
//     public void removeFromCart(Long bookId) {

//         User user = SecurityUtil.getCurrentUser();
//         Cart cart = cartRepository.findByUserAndStatus(user, "active")
//                 .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

//         Book book = bookRepository.findById(bookId)
//                 .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

//         CartItem item = cartItemRepository.findByCartAndBook(cart, book)
//                 .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

//         cartItemRepository.delete(item);
//     }

//     @Override
//     public List<CartItemResponse> getMyCart() {

//         User user = SecurityUtil.getCurrentUser();
//         Cart cart = cartRepository.findByUserAndStatus(user, "active")
//                 .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

//         return cartItemRepository.findByCart(cart)
//                 .stream()
//                 .map(CartMapper::toResponse)
//                 .toList();
//     }
// }

package com.example.myapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.entity.Book;
import com.example.myapp.entity.Cart;
import com.example.myapp.entity.CartItem;
import com.example.myapp.entity.User;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.model.response.CartResponse;
import com.example.myapp.repository.BookRepository;
import com.example.myapp.repository.CartItemRepository;
import com.example.myapp.repository.*;
import com.example.myapp.security.SecurityUtil;
import com.example.myapp.service.*;
import com.example.myapp.util.CartMapper;

@Service
public class CartServiceImpl implements CartService {

        private final CartRepository cartRepository;
        private final CartItemRepository cartItemRepository;
        private final BookRepository bookRepository;
        private final CartMapper cartMapper;

        public CartServiceImpl(
                        CartRepository cartRepository,
                        CartItemRepository cartItemRepository,
                        BookRepository bookRepository,
                        CartMapper cartMapper) {

                this.cartRepository = cartRepository;
                this.cartItemRepository = cartItemRepository;
                this.bookRepository = bookRepository;
                this.cartMapper = cartMapper;
        }

        /* ================== ADD TO CART ================== */

        @Override
        public void addToCart(Long bookId, int quantity) {

                if (quantity <= 0) {
                        throw new AppException(ErrorCode.INVALID_QUANTITY);
                }

                User user = SecurityUtil.getCurrentUser();

                Cart cart = cartRepository.findByUserAndStatus(user, "active")
                                .orElseGet(() -> cartRepository.save(
                                                new Cart(null, user, "active", LocalDateTime.now())));

                Book book = bookRepository.findById(bookId)
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

                CartItem cartItem = cartItemRepository.findByCartAndBook(cart, book)
                                .orElseGet(() -> new CartItem(
                                                null,
                                                cart,
                                                book,
                                                0,
                                                book.getPrice()));

                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemRepository.save(cartItem);
        }

        /* ================== REMOVE ITEM ================== */

        @Override
        public void removeFromCart(Long bookId) {

                User user = SecurityUtil.getCurrentUser();

                Cart cart = cartRepository.findByUserAndStatus(user, "active")
                                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

                Book book = bookRepository.findById(bookId)
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

                CartItem item = cartItemRepository.findByCartAndBook(cart, book)
                                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

                cartItemRepository.delete(item);
        }

        /* ================== VIEW CART ================== */

        @Override
        public CartResponse getMyCart() {

                User user = SecurityUtil.getCurrentUser();

                Cart cart = cartRepository.findByUserAndStatus(user, "active")
                                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

                List<CartItem> cartItems = cartItemRepository.findByCart(cart);
                return cartMapper.toResponse(cart, cartItems);
        }
}
