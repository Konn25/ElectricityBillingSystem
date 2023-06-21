package com.billing.ElectricityBillingSystem.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "payment_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCategory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "price")
    double price;


}
