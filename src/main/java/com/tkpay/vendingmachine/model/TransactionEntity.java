package com.tkpay.vendingmachine.model;

import com.tkpay.vendingmachine.enums.Currencies;
import com.tkpay.vendingmachine.enums.TransactionStatus;
import com.tkpay.vendingmachine.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Table(name = "transaction")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @NotNull(message = "Currency is required!")
    @Enumerated(EnumType.STRING)
    private Currencies currency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant createdDate;

    @UpdateTimestamp
    private Instant lastUpdatedDate;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false, updatable = false)
    private WalletEntity wallet;

    @ManyToMany
    @JoinTable(
            name = "transaction_product",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> products;
}
