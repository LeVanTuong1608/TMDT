package com.example.myapp.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreatePaymentRequest {
    private Long orderId;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentDate;
    private String paymentAmount;
    private String paymentCurrency;
}
