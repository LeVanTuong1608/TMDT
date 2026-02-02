package com.example.myapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "order_discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_discount_id")
    private Long id;

    private String discountName;

    @Column(unique = true)
    private String discountCode;

    private String discountType; // PERCENT | FIXED

    private BigDecimal discountValue;

    private BigDecimal minOrderAmount;

    private LocalDate startDate;
    private LocalDate endDate;
}
