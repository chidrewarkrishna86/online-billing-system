package com.billing.controller;

import com.billing.entity.Invoice;
import com.billing.entity.Customer;
import com.billing.service.InvoiceService;
import com.billing.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/billing/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final CustomerService customerService;

    public InvoiceController(InvoiceService invoiceService, CustomerService customerService) {
        this.invoiceService = invoiceService;
        this.customerService = customerService;
    }

    @GetMapping
    public String listInvoices(@RequestParam(required = false) String status, Model model) {
        List<Invoice> invoices;
        if (status != null && !status.isEmpty()) {
            invoices = invoiceService.getInvoicesByStatus(Invoice.InvoiceStatus.valueOf(status));
        } else {
            invoices = invoiceService.getAllInvoices();
        }
        model.addAttribute("invoices", invoices);
        model.addAttribute("statuses", Invoice.InvoiceStatus.values());
        model.addAttribute("currentStatus", status);
        return "invoices/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Customer> customers = customerService.getActiveCustomers();
        model.addAttribute("invoice", new Invoice());
        model.addAttribute("customers", customers);
        return "invoices/form";
    }

    @GetMapping("/{id}/view")
    public String viewInvoice(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        model.addAttribute("invoice", invoice);
        return "invoices/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        List<Customer> customers = customerService.getActiveCustomers();
        model.addAttribute("invoice", invoice);
        model.addAttribute("customers", customers);
        return "invoices/form";
    }

    @PostMapping("/{id}/delete")
    public String deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return "redirect:/billing/invoices";
    }

}