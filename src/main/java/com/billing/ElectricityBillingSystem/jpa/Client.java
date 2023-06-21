package com.billing.ElectricityBillingSystem.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

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

    @Column(name = "customer_name", nullable = false)
    String name;

    @Column(name = "meterId", nullable = false)
    Long meterId;

    @Column(name="postal_code",nullable = false)
    int postalCode;

    @Column(name="city",nullable = false)
    String city;

    @Column(name="street",nullable = false)
    String street;

    @Column(name="number",nullable = false)
    int houseNumber;

    @ManyToOne(targetEntity = PaymentCategory.class, fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    @NotNull
    int paymentCategoryId;

}
