package com.example.myapp.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.entity.Book;
import com.example.myapp.entity.Cart;
import com.example.myapp.entity.CartItem;
import com.example.myapp.entity.Order;
import com.example.myapp.entity.OrderItem;
import com.example.myapp.entity.User;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.model.response.CartItemResponse;
import com.example.myapp.model.response.CartResponse;
import com.example.myapp.repository.BookRepository;
import com.example.myapp.repository.CartItemRepository;
import com.example.myapp.repository.CartRepository;
import com.example.myapp.repository.OrderItemRepository;
import com.example.myapp.repository.OrderRepository;
import com.example.myapp.security.SecurityUtil;
import com.example.myapp.service.CartService;
import com.example.myapp.util.CartMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

        private final CartRepository cartRepository;
        private final CartItemRepository cartItemRepository;
        private final BookRepository bookRepository;
        private final CartMapper cartMapper;
        private final OrderRepository orderRepository;
        private final OrderItemRepository orderItemRepository;

        @Override
        public void addToCart(Long bookId, int quantity) {

                if (quantity <= 0) {
                        throw new AppException(ErrorCode.INVALID_QUANTITY);
                }

                User user = SecurityUtil.getCurrentUser();

                Cart cart = cartRepository.findByUserAndStatus(user, "active")
                                .orElseGet(() -> {
                                        Cart newCart = new Cart();
                                        newCart.setUser(user);
                                        newCart.setStatus("active");
                                        newCart.setCreatedAt(LocalDateTime.now());
                                        return cartRepository.save(newCart);
                                });

                Book book = bookRepository.findById(bookId)
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

                CartItem cartItem = cartItemRepository.findByCartAndBook(cart, book)
                                .orElse(new CartItem(null, cart, book, 0, book.getPrice()));

                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemRepository.save(cartItem);
        }

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

        @Override
        public CartResponse getMyCart() {

                User user = SecurityUtil.getCurrentUser();

                Cart cart = cartRepository.findByUserAndStatus(user, "active")
                                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

                List<CartItem> cartItems = cartItemRepository.findByCart(cart);
                return cartMapper.toResponse(cart, cartItems);
        }

        @Override
        public void updateQuantity(Long bookId, int quantity) {
                if (quantity <= 0) {
                        removeFromCart(bookId);
                        return;
                }

                User user = SecurityUtil.getCurrentUser();
                Cart cart = cartRepository.findByUserAndStatus(user, "active")
                                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

                Book book = bookRepository.findById(bookId)
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

                CartItem item = cartItemRepository.findByCartAndBook(cart, book)
                                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

                item.setQuantity(quantity);
                cartItemRepository.save(item);
        }

        @Override
        public void clearCart() {
                User user = SecurityUtil.getCurrentUser();
                Cart cart = cartRepository.findByUserAndStatus(user, "active")
                                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

                List<CartItem> items = cartItemRepository.findByCart(cart);
                cartItemRepository.deleteAll(items);
        }

        @Override
        public void checkout() {
                User user = SecurityUtil.getCurrentUser();
                Cart cart = cartRepository.findByUserAndStatus(user, "active")
                                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

                List<CartItem> cartItems = cartItemRepository.findByCart(cart);
                if (cartItems.isEmpty()) {
                        throw new AppException(ErrorCode.INVALID_REQUEST);
                }

                BigDecimal totalAmount = cartItems.stream()
                                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                Order order = new Order();
                order.setUser(user);
                order.setOrderDate(LocalDateTime.now());
                order.setTotalAmount(totalAmount);
                order.setStatus("PENDING");

                Order savedOrder = orderRepository.save(order);

                for (CartItem cartItem : cartItems) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrder(savedOrder);
                        orderItem.setBook(cartItem.getBook());
                        orderItem.setQuantity(cartItem.getQuantity());
                        orderItem.setPrice(cartItem.getPrice());
                        orderItem.setSubtotal(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                        orderItemRepository.save(orderItem);
                }

                clearCart();
        }
}
