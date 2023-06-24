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
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "clientId", nullable = false)
    Long clientId;

    @Column(name = "year", nullable = false)
    int year;

    @Column(name = "month", nullable = false)
    int month;

    @Column(name = "payment", nullable = false)
    double payment;

    @Column(name = "completed",nullable = false)
    int completed;

}
