package com.ashokjeph.dualdb.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashokjeph.dualdb.invoices.InvoicesService;
import com.ashokjeph.dualdb.invoices.model.AddInvoicesRequestModel;
import com.ashokjeph.dualdb.invoices.model.ManualInvoiceRequestModel;
import com.ashokjeph.dualdb.invoices_webhook.model.GetInvoiceListRequestModel;

@RestController
@RequestMapping("/invoices/v1")
public class InvoiceController {

    @Autowired
    private InvoicesService invoicesService;

    @PostMapping("/addInvoices")
    public ResponseEntity<?> addInvoices(@Valid @RequestBody AddInvoicesRequestModel requestModel){
        return invoicesService.addInvoices(requestModel);
    }

    @PostMapping("/getInvoice")
    public ResponseEntity<?> getInvoicesList(@RequestBody GetInvoiceListRequestModel requestModel){

        return invoicesService.getInvoicesList(requestModel);
    }

    @PostMapping("/addManualInvoices")
    public ResponseEntity<?> addManualInvoices(@Valid @RequestBody ManualInvoiceRequestModel requestModel) {
        return invoicesService.addManualInvoices(requestModel);
    }

}
