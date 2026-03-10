package com.example.myapp.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class PaymentRequest {
    private Long orderId;
    private String paymentMethod;

}
