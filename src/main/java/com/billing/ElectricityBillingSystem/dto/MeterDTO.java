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
public class MeterDTO {

    @JsonIgnore
    Long id;

    Long clientId;

    public MeterDTO(Long clientId) {
        this.clientId = clientId;
    }
}
