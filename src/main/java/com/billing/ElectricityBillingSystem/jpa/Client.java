package com.billing.ElectricityBillingSystem.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "customerName", nullable = false)
    String name;

    @Column(name = "meterId", nullable = false)
    Long meterId;

    @Column(name="postalCode",nullable = false)
    int postalCode;

    @Column(name="city",nullable = false)
    String city;

    @Column(name="street",nullable = false)
    String street;

    @Column(name="number",nullable = false)
    int houseNumber;

    @Column(name = "categoryId", nullable = false)
    int paymentCategoryId;

}
