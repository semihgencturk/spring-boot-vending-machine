package com.tkpay.vendingmachine.model;

import com.tkpay.vendingmachine.enums.Currencies;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Table(name = "wallet")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotNull(message = "Balance is required!")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    private BigDecimal balance;

    @Column(nullable = false)
    @NotNull(message = "Currency is required!")
    @Enumerated(EnumType.STRING)
    private Currencies currency;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private AccountEntity account;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdDate;

    @Column(nullable = false)
    @UpdateTimestamp
    private Instant lastUpdatedDate;
}
