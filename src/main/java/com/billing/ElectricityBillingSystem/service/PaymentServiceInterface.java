package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentServiceInterface {


    Payment createNewPayment(Payment payment);

    Optional<Payment> getClientAllPayment(Long clientId);

    Payment clientPayingBill(Long clientId, Long paymentId);

    List<Payment> getClientAllPaidBill(Long clientId);

    List<Payment> getClientAllNotPaidBill(Long clientId);

    List<Payment> getAllBill();

}
