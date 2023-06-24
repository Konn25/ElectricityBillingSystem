package com.billing.ElectricityBillingSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class PaymentDTO {

    @JsonIgnore
    Long id;

    Long clientId;

    int year;

    int month;

    double payment;

    int completed;

    public PaymentDTO(Long clientId, int year, int month, double payment, int completed) {
        this.clientId = clientId;
        this.year = year;
        this.month = month;
        this.payment = payment;
        this.completed = completed;
    }
}
