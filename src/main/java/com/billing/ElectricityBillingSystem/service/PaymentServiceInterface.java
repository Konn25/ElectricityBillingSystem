package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Payment;

import java.util.List;


public interface PaymentServiceInterface {


    Payment createNewPayment(Payment payment);

    List<Payment> getClientAllPayment(Long clientId);

    Payment clientPayingBill(Long clientId, Long paymentId);

    List<Payment> getClientAllPaidBill(Long clientId);

    List<Payment> getClientAllNotPaidBill(Long clientId);

    List<Payment> getAllBill();

}
