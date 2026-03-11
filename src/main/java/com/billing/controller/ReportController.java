package com.billing.controller;

import com.billing.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/billing/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public String reports() {
        return "reports/index";
    }

    @GetMapping("/revenue")
    public String revenueReport(@RequestParam(required = false) String startDate,
                               @RequestParam(required = false) String endDate,
                               Model model) {
        LocalDate start = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate) : LocalDate.now().minusMonths(1);
        LocalDate end = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate) : LocalDate.now();

        Map<String, Object> report = reportService.getRevenueReport(start, end);
        model.addAttribute("report", report);
        model.addAttribute("startDate", start);
        model.addAttribute("endDate", end);

        return "reports/revenue";
    }

    @GetMapping("/payments")
    public String paymentReport(@RequestParam(required = false) String startDate,
                               @RequestParam(required = false) String endDate,
                               Model model) {
        LocalDate start = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate) : LocalDate.now().minusMonths(1);
        LocalDate end = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate) : LocalDate.now();

        Map<String, Object> report = reportService.getPaymentReport(start, end);
        model.addAttribute("report", report);
        model.addAttribute("startDate", start);
        model.addAttribute("endDate", end);

        return "reports/payments";
    }

    @GetMapping("/invoice-status")
    public String invoiceStatusReport(Model model) {
        Map<String, Object> report = reportService.getInvoiceStatusReport();
        model.addAttribute("report", report);
        return "reports/invoice-status";
    }

}