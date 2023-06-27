package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Consumption;
import com.billing.ElectricityBillingSystem.jpa.Payment;
import com.billing.ElectricityBillingSystem.jpa.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentServiceInterface {


    private final PaymentRepository paymentRepository;


    @Override
    public Payment createNewPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getClientAllPayment(Long clientId) {
        return paymentRepository.findPaymentsByClientId(clientId);
    }

    @Override
    public Payment clientPayingBill(Long clientId, Long paymentId) {

        Optional<Payment> findPayment = paymentRepository.findPaymentByClientId(clientId);

        if (findPayment.get().getId().equals(paymentId)) {
            Payment payment = findPayment.get();
            payment.setCompleted(1);
            return paymentRepository.save(payment);
        }

        return null;
    }

    @Override
    public List<Payment> getClientAllPaidBill(Long clientId) {

        List<Payment> findPayment = paymentRepository.findPaymentsByClientId(clientId);

        return findPayment.stream().filter(v -> v.getCompleted() == 1).toList();
    }

    @Override
    public List<Payment> getClientAllNotPaidBill(Long clientId) {

        List<Payment> findPayment = paymentRepository.findPaymentsByClientId(clientId);

        return findPayment.stream().filter(v -> v.getCompleted() == 0).toList();
    }

    @Override
    public List<Payment> getAllBill() {
        return paymentRepository.findAll();
    }
}
