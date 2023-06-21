package com.billing.ElectricityBillingSystem.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

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

    @ManyToOne(targetEntity = Meter.class, fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "meterId", referencedColumnName = "id")
    @NotNull
    Long meterId;

    @Column(name = "year")
    int year;

    @Column(name = "month")
    String month;

    @Column(name = "day")
    int day;

    @Column(name = "consumption")
    double consumption;

}
