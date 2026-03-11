package com.billing.service;

import com.billing.entity.Invoice;
import com.billing.entity.Payment;
import com.billing.repository.InvoiceRepository;
import com.billing.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReportService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    public ReportService(InvoiceRepository invoiceRepository, PaymentRepository paymentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
    }

    public Map<String, Object> getRevenueReport(LocalDate startDate, LocalDate endDate) {
        List<Invoice> invoices = invoiceRepository.findInvoicesByDateRange(startDate, endDate);
        
        BigDecimal totalRevenue = invoices.stream()
                .map(Invoice::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalInvoices = invoices.size();
        long paidInvoices = invoices.stream()
                .filter(i -> i.getStatus() == Invoice.InvoiceStatus.PAID)
                .count();

        Map<String, Object> report = new HashMap<>();
        report.put("startDate", startDate);
        report.put("endDate", endDate);
        report.put("totalRevenue", totalRevenue);
        report.put("totalInvoices", totalInvoices);
        report.put("paidInvoices", paidInvoices);
        report.put("pendingInvoices", totalInvoices - paidInvoices);

        return report;
    }

    public Map<String, Object> getPaymentReport(LocalDate startDate, LocalDate endDate) {
        List<Payment> payments = paymentRepository.findPaymentsByDateRange(startDate, endDate);
        BigDecimal totalCollected = paymentRepository.getTotalPaymentsByDateRange(startDate, endDate);

        if (totalCollected == null) {
            totalCollected = BigDecimal.ZERO;
        }

        Map<String, Object> report = new HashMap<>();
        report.put("startDate", startDate);
        report.put("endDate", endDate);
        report.put("totalPayments", payments.size());
        report.put("totalCollected", totalCollected);
        report.put("averagePayment", payments.isEmpty() ? BigDecimal.ZERO : 
                  totalCollected.divide(new BigDecimal(payments.size())));

        return report;
    }

    public Map<String, Object> getInvoiceStatusReport() {
        List<Invoice> allInvoices = invoiceRepository.findAll();

        Map<String, Long> statusCount = new HashMap<>();
        for (Invoice.InvoiceStatus status : Invoice.InvoiceStatus.values()) {
            long count = allInvoices.stream()
                    .filter(i -> i.getStatus() == status)
                    .count();
            if (count > 0) {
                statusCount.put(status.toString(), count);
            }
        }

        Map<String, Object> report = new HashMap<>();
        report.put("statusBreakdown", statusCount);
        report.put("totalInvoices", allInvoices.size());

        return report;
    }

    public Map<String, Object> getCustomerSalesReport(Long customerId) {
        List<Invoice> customerInvoices = invoiceRepository.findByCustomerId(customerId);

        BigDecimal totalSales = customerInvoices.stream()
                .map(Invoice::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPaid = customerInvoices.stream()
                .map(Invoice::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> report = new HashMap<>();
        report.put("customerId", customerId);
        report.put("totalInvoices", customerInvoices.size());
        report.put("totalSales", totalSales);
        report.put("totalPaid", totalPaid);
        report.put("totalDue", totalSales.subtract(totalPaid));

        return report;
    }

}