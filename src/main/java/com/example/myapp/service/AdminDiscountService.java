package com.example.myapp.service;

import com.example.myapp.model.request.DiscountRequest;
import com.example.myapp.model.response.DiscountResponse;
import com.example.myapp.model.response.PageResponse;

public interface AdminDiscountService {
    DiscountResponse createDiscount(DiscountRequest request);

    DiscountResponse updateDiscount(Long id, DiscountRequest request);

    void deleteDiscount(Long id);

    PageResponse<DiscountResponse> getDiscounts(int page, int size);

    PageResponse<DiscountResponse> searchDiscounts(String keyword, int page, int size);

    DiscountResponse getDetail(Long id);

}
