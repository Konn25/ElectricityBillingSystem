package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.dto.PaymentDTO;
import com.billing.ElectricityBillingSystem.jpa.PaymentCategory;
import com.billing.ElectricityBillingSystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PaymentController {

    private final ModelMapper modelMapper;

    private final PaymentService paymentService;

    @PostMapping("/paymentcategory/registration")
    @ResponseBody
    public ResponseEntity<?> registerNewAdmin(@RequestBody PaymentDTO paymentDTO) {

        PaymentCategory paymentCategory = modelMapper.map(paymentDTO, PaymentCategory.class);

        List<PaymentCategory> paymentCategoryList = paymentService.getAllPaymentCategory();

        for (PaymentCategory value : paymentCategoryList) {
            if(value.getPrice() == paymentCategory.getPrice()){
                return  ResponseEntity.status(HttpStatus.CONFLICT).body("This price is already registered");
            }
        }

        PaymentCategory newPaymentCategory = paymentService.createNewPaymentCategory(paymentCategory.getPrice());

        PaymentDTO paymentResponse = modelMapper.map(newPaymentCategory,PaymentDTO.class);

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/paymentcategory/all")
    @ResponseBody
    public List<PaymentCategory> getAllPaymentCategory(){
        return paymentService.getAllPaymentCategory();
    }

}
