package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.PaymentCategory;
import com.billing.ElectricityBillingSystem.jpa.PaymentCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentServiceInterface{

    private final PaymentCategoryRepository paymentCategoryRepository;
    @Override
    public PaymentCategory createNewPaymentCategory(double price) {

        PaymentCategory paymentCategory = new PaymentCategory();
        paymentCategory.setPrice(price);

        return paymentCategoryRepository.save(paymentCategory);
    }

    @Override
    public List<PaymentCategory> getAllPaymentCategory() {
        return paymentCategoryRepository.findAll();
    }
}
