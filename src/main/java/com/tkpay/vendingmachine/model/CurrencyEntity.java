package com.tkpay.vendingmachine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Table(name = "currency")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true, updatable = false)
    @Pattern(regexp = "\\d{3}", message = "Numeric code must be 3-digit number according to ISO!")
    @NotNull(message = "Numeric code is required!")
    private String numericCode;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true, updatable = false)
    @Pattern(regexp = "[A-Z]{3}", message = "Alphabetic code must be 3 uppercase letter according to ISO!")
    @NotBlank(message = "Alphabetic code is required!")
    private String alphabeticCode;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Name is required!")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Status is required!")
    private boolean status;
}
