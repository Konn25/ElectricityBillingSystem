package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.PaymentCategory;

import java.util.List;

public interface PaymentCategoryServiceInterface {

    PaymentCategory createNewPaymentCategory(double price);

    List<PaymentCategory> getAllPaymentCategory();

    Double calculatePaymentByPaymentCategory(Long clientId, int year, int month);

}
