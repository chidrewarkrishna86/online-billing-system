package com.billing.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String invoiceNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private LocalDate invoiceDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.PENDING;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @Column(length = 500)
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<InvoiceItem> items;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (invoiceNumber == null) {
            invoiceNumber = "INV-" + System.currentTimeMillis();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public BigDecimal getBalance() {
        return totalAmount.subtract(paidAmount);
    }

    public enum InvoiceStatus {
        DRAFT, PENDING, SENT, PAID, PARTIALLY_PAID, CANCELLED, OVERDUE
    }

}