package com.billing.ElectricityBillingSystem.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PaymentCategoryDTO {

    @JsonIgnore
    Long id;

    double price;

    public PaymentCategoryDTO(double price) {
        this.price = price;
    }
}
