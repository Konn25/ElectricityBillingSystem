package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.dto.PaymentDTO;
import com.billing.ElectricityBillingSystem.jpa.Consumption;
import com.billing.ElectricityBillingSystem.jpa.Payment;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ConsumptionServiceInterface {

    Optional<Consumption> getAllConsumption(Long meterId);

    Optional<Consumption> checkConsumption(Consumption consumption);

    Consumption createNewConsumption(Consumption newConsumption);

    List<Consumption> getConsumptionByYear(Long meterId, int year);

    Double getAllConsumptionByYear(Long meterId, int year);

    Optional<Consumption> getConsumptionByYearAndMonth(Long meterId, int year, int month);

    ResponseEntity<?> checkAllPayment(List<Payment> paymentsList, PaymentDTO paymentDTO);

    ResponseEntity<?> createPaymentToDatabase(Long clientId, int year, int month);

}
