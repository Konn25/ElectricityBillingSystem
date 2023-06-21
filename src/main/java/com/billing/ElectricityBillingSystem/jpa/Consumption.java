package com.billing.ElectricityBillingSystem.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "consumption")
@Getter
@Setter
@NoArgsConstructor
public class Consumption {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "meterId", nullable = false)
    Long meterId;

    @Column(name = "year")
    int year;

    @Column(name = "month")
    int month;

    @Column(name = "day")
    int day;

    @Column(name = "consumption")
    double consumption;

}
