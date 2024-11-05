package com.tkpay.vendingmachine.model;

import com.tkpay.vendingmachine.enums.AccountStatus;
import com.tkpay.vendingmachine.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.UUID;

@Data
@Table(name = "account")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(min = 10, max = 10, message = "CustomerNo must be 10 digits!")
    @Column(nullable = false, updatable = false, unique = true)
    @Pattern(regexp = "\\d{10}", message = "CustomerNo must contain only digits!")
    @NotBlank(message = "CustomerNo is required!")
    private String customerNo;

    @Size(min = 12, max = 16, message = "Password must be between 12 and 16 character!")
    @Column(nullable = false)
    @Pattern(regexp = "(?![._-])(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[._-])[\\w._-]{12,16}",
             message = "Password must meet the following criteria:\n" +
                       "At least one uppercase letter (A-Z)\n" +
                       "At least one lowercase letter (a-z)\n" +
                       "At least one digit (0-9)\n" +
                       "At least one special character (._-)\n" +
                       "Must not start with a special character")
    @NotBlank(message = "Password is required!")
    private String password;

    @Email(message = "Email should be valid!")
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required!")
    private String email;

    @Size(min = 10, max = 15, message = "Phone number must be valid with 10-15 digits!")
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "[1-9][0-9]{10,14}",
             message = " Phone number contains only digits and cannot start with 0!")
    @NotBlank(message = "Phone number is required!")
    private String phoneNumber;

    @Size(max = 35)
    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name can only contains letters.")
    @NotBlank(message = "Name is required!")
    private String name;

    @Size(max = 35)
    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Surname can only contains letters.")
    @NotBlank(message = "Surname is required!")
    private String surname;

    @Past
    @Column(nullable = false)
    @NotNull(message = "Birthdate is required!")
    private LocalDate birthDate;

    @Column(nullable = false)
    @NotNull(message = "Account Type is required!")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(nullable = false)
    @NotNull(message = "Account Status is required!")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @OneToMany(mappedBy = "account", orphanRemoval = false)
    private Set<WalletEntity> wallets;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdDate;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    @UpdateTimestamp
    private Instant lastUpdatedDate;

    private void checkAndAssignTypeBasedOnAge() {
        int age = Period.between(this.birthDate, LocalDate.now()).getYears();
        if(age <= 13){
            this.type = AccountType.CHILD;
        }
    }

    @PrePersist
    private void beforePersist() {
        checkAndAssignTypeBasedOnAge();
    }

    @PreUpdate
    private void beforeUpdate() {
        checkAndAssignTypeBasedOnAge();
    }
}
