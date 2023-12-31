package com.billing.ElectricityBillingSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ConsumptionDTO {

    @JsonIgnore
    Long id;

    Long meterId;

    int year;

    int month;

    int day;

    double consumption;

    public ConsumptionDTO(Long meterId, int year, int month, int day, double consumption) {
        this.meterId = meterId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.consumption = consumption;
    }
}
