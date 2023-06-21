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

    @Column(name = "customername", nullable = false)
    String name;

    @Column(name = "meterId", nullable = false)
    Long meterId;

    @Column(name = "address", nullable = false)
    String address;

    @ManyToOne(targetEntity = PaymentCategory.class, fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    @NotNull
    int paymentCategoryId;

}
