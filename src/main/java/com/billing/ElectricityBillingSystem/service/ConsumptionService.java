package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.dto.PaymentDTO;
import com.billing.ElectricityBillingSystem.jpa.Consumption;
import com.billing.ElectricityBillingSystem.jpa.ConsumptionRepository;
import com.billing.ElectricityBillingSystem.jpa.Payment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsumptionService implements ConsumptionServiceInterface {

    private final ConsumptionRepository consumptionRepository;

    private final PaymentCategoryService paymentCategoryService;

    private final PaymentService paymentService;

    private final ModelMapper modelMapper;

    @Override
    public Optional<Consumption> getAllConsumption(Long meterId) {
        return consumptionRepository.findConsumptionByMeterId(meterId);
    }

    @Override
    public Optional<Consumption> checkConsumption(Consumption consumption) {
        return consumptionRepository.findConsumptionByMeterIdAndYearAndMonth(consumption.getMeterId(), consumption.getYear(), consumption.getMonth());
    }

    @Override
    public Consumption createNewConsumption(Consumption newConsumption) {
        return consumptionRepository.save(newConsumption);
    }

    @Override
    public List<Consumption> getConsumptionByYear(Long meterId, int year) {
        return consumptionRepository.findConsumptionByMeterIdAndYear(meterId, year);

    }

    @Override
    public Double getAllConsumptionByYear(Long meterId, int year) {
        double allConsumption = 0.0;
        List<Consumption> consumptionList = consumptionRepository.findConsumptionByMeterIdAndYear(meterId, year);

        for (Consumption consumption : consumptionList) {
            allConsumption = allConsumption + consumption.getConsumption();

        }

        return allConsumption;

    }

    @Override
    public Optional<Consumption> getConsumptionByYearAndMonth(Long meterId, int year, int month) {
        return consumptionRepository.findConsumptionByMeterIdAndYearAndMonth(meterId, year, month);
    }

    @Override
    public ResponseEntity<?> checkAllPayment(List<Payment> paymentsList, PaymentDTO paymentDTO) {

        for (Payment x : paymentsList) {
            if (x.getClientId().equals(paymentDTO.getClientId()) && x.getYear() == paymentDTO.getYear() && x.getMonth() == paymentDTO.getMonth()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This payment is already registered!");
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @Override
    public ResponseEntity<?> createPaymentToDatabase(Long clientId, int year, int month) {

        double actualPayment = paymentCategoryService.calculatePaymentByPaymentCategory(clientId, year, month);

        if (actualPayment == 0.0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The payment is zero!!");
        }

        PaymentDTO paymentDTO = new PaymentDTO(clientId, year, month, actualPayment, 0);

        Payment payment = modelMapper.map(paymentDTO, Payment.class);

        paymentService.createNewPayment(payment);

        return ResponseEntity.status(HttpStatus.OK).body("This payment is already registered!");

    }
}
