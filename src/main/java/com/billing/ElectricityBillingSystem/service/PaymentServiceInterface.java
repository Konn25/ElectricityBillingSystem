package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Consumption;
import com.billing.ElectricityBillingSystem.jpa.PaymentCategory;

import java.util.List;

public interface PaymentServiceInterface {

    PaymentCategory createNewPaymentCategory(double price);

    List<PaymentCategory> getAllPaymentCategory();

}
