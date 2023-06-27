package com.billing.ElectricityBillingSystem.controller;


import com.billing.ElectricityBillingSystem.dto.PaymentDTO;
import com.billing.ElectricityBillingSystem.jpa.ClientRepository;
import com.billing.ElectricityBillingSystem.jpa.Payment;
import com.billing.ElectricityBillingSystem.jpa.PaymentRepository;
import com.billing.ElectricityBillingSystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PaymentController {

    private final ModelMapper modelMapper;

    private final PaymentService paymentService;

    @PostMapping("/payment/register")
    @ResponseBody
    public ResponseEntity<?> registerNewPayment(@RequestBody PaymentDTO paymentDTO) {

        Payment payment = modelMapper.map(paymentDTO, Payment.class);

        List<Payment> paymentCategoryList = paymentService.getAllBill();

        if (paymentCategoryList.stream().anyMatch(x -> x.getClientId().equals(paymentDTO.getClientId()) && x.getYear() == paymentDTO.getYear() && x.getMonth() == paymentDTO.getMonth())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This payment is already registered!");
        }


        Payment newPayment = paymentService.createNewPayment(payment);

        PaymentDTO paymentResponse = modelMapper.map(newPayment, PaymentDTO.class);

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/payment/all/{clientId}")
    @ResponseBody
    public List<Payment> getClientAllPayment(@PathVariable(value = "clientId") Long clientId) {
        return paymentService.getClientAllPayment(clientId);
    }

    @PostMapping("/payment/paying/{clientId}/{paymentId}")
    @ResponseBody
    public Payment clientPayingBill(@PathVariable(value = "clientId") Long clientId, @PathVariable(value = "paymentId") Long paymentId) {
        return paymentService.clientPayingBill(clientId, paymentId);
    }

    @GetMapping("/payment/bill/paid/{clientId}")
    @ResponseBody
    public List<Payment> getClientAllPaidBill(@PathVariable(value = "clientId") Long clientId) {
        return paymentService.getClientAllPaidBill(clientId);
    }

    @GetMapping("/payment/bill/notpaid/{clientId}")
    @ResponseBody
    public List<Payment> getClientAllNotPaidBill(@PathVariable(value = "clientId") Long clientId) {
        return paymentService.getClientAllNotPaidBill(clientId);
    }

}
