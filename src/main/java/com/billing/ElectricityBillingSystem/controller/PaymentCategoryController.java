package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.dto.PaymentCategoryDTO;
import com.billing.ElectricityBillingSystem.jpa.PaymentCategory;
import com.billing.ElectricityBillingSystem.service.PaymentCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Tag(name = "Payment Category API", description = "The Payment Category API.")
public class PaymentCategoryController {

    private final ModelMapper modelMapper;

    private final PaymentCategoryService paymentCategoryService;

    @PostMapping("/paymentcategory/registration")
    @ResponseBody
    @Operation(summary = "Create new payment category", description = "Create new payment category")
    @ApiResponse(responseCode = "201", description = "Create new payment category")
    @ApiResponse(responseCode = "400", description = "This price already registered")
    public ResponseEntity<?> registerNewCategory(@RequestBody PaymentCategoryDTO paymentCategoryDTO) {

        PaymentCategory paymentCategory = modelMapper.map(paymentCategoryDTO, PaymentCategory.class);

        List<PaymentCategory> paymentCategoryList = paymentCategoryService.getAllPaymentCategory();

        for (PaymentCategory value : paymentCategoryList) {
            if (value.getPrice() == paymentCategory.getPrice()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("This price is already registered");
            }
        }

        PaymentCategory newPaymentCategory = paymentCategoryService.createNewPaymentCategory(paymentCategory.getPrice());

        PaymentCategoryDTO paymentResponse = modelMapper.map(newPaymentCategory, PaymentCategoryDTO.class);

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/paymentcategory/all")
    @ResponseBody
    @SecurityRequirement(name = "bearerToken")
    @Operation(summary = "Get all payment category", description = "Get all payment category")
    @ApiResponse(responseCode = "200", description = "Get all payment category")
    public List<PaymentCategory> getAllPaymentCategory() {
        return paymentCategoryService.getAllPaymentCategory();
    }

    @GetMapping("/paymentcategory/payment/{clientId}/{year}/{month}")
    @ResponseBody
    @SecurityRequirement(name = "bearerToken")
    @Operation(summary = "Get client's payment category", description = "Get client's payment category")
    @ApiResponse(responseCode = "200", description = "Get client's payment category")
    @ApiResponse(responseCode = "400", description = "Payment id zero or something went wrong")
    public ResponseEntity<?> getActualPayment(@PathVariable(value = "clientId") Long clientId, @PathVariable(value = "year") int year, @PathVariable(value = "month") int month) {

        double actualPayment = paymentCategoryService.calculatePaymentByPaymentCategory(clientId, year, month);

        if (actualPayment != 0.0) {
            return ResponseEntity.status(HttpStatus.OK).body("Price: " + actualPayment);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong! Please check the year and month or add new consumption.");


    }

}
