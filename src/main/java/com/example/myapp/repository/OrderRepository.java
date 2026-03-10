package com.example.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.entity.Order;
import com.example.myapp.entity.User;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    // tìm kiếm đơn hàng theo id của đơn hàng
    Optional<Order> findByIdAndUserId(Long id, Long userId);

    @Query("select sum(o.totalAmount) from Order o")
    BigDecimal sumTotalAmount();
}
