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
public class PaymentDTO {

    @JsonIgnore
    Long id;

    double price;

    public PaymentDTO(double price) {
        this.price = price;
    }
}
