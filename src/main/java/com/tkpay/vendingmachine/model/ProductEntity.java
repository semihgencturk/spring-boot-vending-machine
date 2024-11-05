package com.tkpay.vendingmachine.model;

import com.tkpay.vendingmachine.enums.Currencies;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@Table(name = "product")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Product name is required!")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Amount is required!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount cannot be negative")
    private BigDecimal amount;

    @Column(nullable = false)
    @NotNull(message = "Currency is required!")
    @Enumerated(EnumType.STRING)
    private Currencies currency;

    @Min(0)
    @Column(nullable = false)
    @NotNull(message = "Count is required!")
    private int count;
}
