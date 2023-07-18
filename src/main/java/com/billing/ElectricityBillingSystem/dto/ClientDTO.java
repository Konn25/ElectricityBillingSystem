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
public class ClientDTO {

    @JsonIgnore
    Long id;

    String name;

    Long meterId;

    int postalCode;

    String city;

    String street;

    int houseNumber;

    int paymentCategoryId;

    String email;

    String password;



}
