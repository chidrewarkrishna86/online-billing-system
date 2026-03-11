package com.billing.service;

import com.billing.entity.*;
import com.billing.repository.InvoiceRepository;
import com.billing.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, PaymentRepository paymentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
    }

    public Invoice createInvoice(Invoice invoice) {
        calculateInvoiceTotals(invoice);
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        invoice.setCustomer(invoiceDetails.getCustomer());
        invoice.setInvoiceDate(invoiceDetails.getInvoiceDate());
        invoice.setDueDate(invoiceDetails.getDueDate());
        invoice.setStatus(invoiceDetails.getStatus());
        invoice.setNotes(invoiceDetails.getNotes());
        invoice.setItems(invoiceDetails.getItems());

        calculateInvoiceTotals(invoice);
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getInvoicesByCustomer(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }

    public List<Invoice> getInvoicesByStatus(Invoice.InvoiceStatus status) {
        return invoiceRepository.findByStatus(status);
    }

    public List<Invoice> getInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.findInvoicesByDateRange(startDate, endDate);
    }

    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        invoice.setStatus(Invoice.InvoiceStatus.CANCELLED);
        invoiceRepository.save(invoice);
    }

    public Invoice recordPayment(Long invoiceId, Payment payment) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        payment.setInvoice(invoice);
        paymentRepository.save(payment);

        BigDecimal previousPaid = invoice.getPaidAmount();
        invoice.setPaidAmount(previousPaid.add(payment.getAmount()));

        if (invoice.getPaidAmount().compareTo(invoice.getTotalAmount()) >= 0) {
            invoice.setStatus(Invoice.InvoiceStatus.PAID);
        } else if (invoice.getPaidAmount().compareTo(BigDecimal.ZERO) > 0) {
            invoice.setStatus(Invoice.InvoiceStatus.PARTIALLY_PAID);
        }

        return invoiceRepository.save(invoice);
    }

    private void calculateInvoiceTotals(Invoice invoice) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;

        if (invoice.getItems() != null) {
            for (InvoiceItem item : invoice.getItems()) {
                BigDecimal itemAmount = new BigDecimal(item.getQuantity()).multiply(item.getUnitPrice());
                item.setAmount(itemAmount);
                subtotal = subtotal.add(itemAmount);
                totalTax = totalTax.add(item.getTaxAmount());
            }
        }

        invoice.setSubtotal(subtotal);
        invoice.setTaxAmount(totalTax);
        invoice.setTotalAmount(subtotal.add(totalTax));
    }

}