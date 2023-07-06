package com.billing.ElectricityBillingSystem.controller;


import com.billing.ElectricityBillingSystem.dto.PaymentDTO;
import com.billing.ElectricityBillingSystem.jpa.ClientRepository;
import com.billing.ElectricityBillingSystem.jpa.Payment;
import com.billing.ElectricityBillingSystem.jpa.PaymentRepository;
import com.billing.ElectricityBillingSystem.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "The Payment API.")
public class PaymentController {

    private final ModelMapper modelMapper;

    private final PaymentService paymentService;

    @PostMapping("/payment/register")
    @ResponseBody
    @Operation(summary = "Create new payment", description = "Create new payment ")
    @ApiResponse(responseCode = "201", description = "Create new payment ")
    @ApiResponse(responseCode = "400", description = "This payment already registered")
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
    @Operation(summary = "Get client all payments", description = "Get client all payments by client id ")
    @ApiResponse(responseCode = "200", description = "Get client all payments")
    public List<Payment> getClientAllPayment(@PathVariable(value = "clientId") Long clientId) {
        return paymentService.getClientAllPayment(clientId);
    }

    @PostMapping("/payment/paying/{clientId}/{paymentId}")
    @ResponseBody
    @Operation(summary = "Pay client payment", description = "Pay client payment by client id and payment id ")
    @ApiResponse(responseCode = "200", description = "Pay client payment")
    public Payment clientPayingBill(@PathVariable(value = "clientId") Long clientId, @PathVariable(value = "paymentId") Long paymentId) {
        return paymentService.clientPayingBill(clientId, paymentId);
    }

    @GetMapping("/payment/bill/paid/{clientId}")
    @ResponseBody
    @Operation(summary = "Get client all paid bill", description = "Get client all paid bill by client id")
    @ApiResponse(responseCode = "200", description = "Get client all paid bill")
    public List<Payment> getClientAllPaidBill(@PathVariable(value = "clientId") Long clientId) {
        return paymentService.getClientAllPaidBill(clientId);
    }

    @GetMapping("/payment/bill/notpaid/{clientId}")
    @ResponseBody
    @Operation(summary = "Get client all not paid bill", description = "Get client all not paid bill by client id")
    @ApiResponse(responseCode = "200", description = "Get client all not paid bill")
    public List<Payment> getClientAllNotPaidBill(@PathVariable(value = "clientId") Long clientId) {
        return paymentService.getClientAllNotPaidBill(clientId);
    }

}
