//package com.ashokjeph.dualdb.contoller;
//
//import ai.funder.server.invoices.InvoicesService;
//import ai.funder.server.invoices.model.AddInvoicesRequestModel;
//import ai.funder.server.invoices.model.ManualInvoiceRequestModel;
//import ai.funder.server.invoices_webhook.model.GetInvoiceListRequestModel;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/invoices/v1")
//public class InvoiceController {
//
//    @Autowired
//    private InvoicesService invoicesService;
//
//    @PostMapping("/addInvoices")
//    public ResponseEntity<?> addInvoices(@Valid @RequestBody AddInvoicesRequestModel requestModel){
//        return invoicesService.addInvoices(requestModel);
//    }
//
//    @PostMapping("/getInvoice")
//    public ResponseEntity<?> getInvoicesList(@RequestBody GetInvoiceListRequestModel requestModel){
//
//        return invoicesService.getInvoicesList(requestModel);
//    }
//
//    @PostMapping("/addManualInvoices")
//    public ResponseEntity<?> addManualInvoices(@Valid @RequestBody ManualInvoiceRequestModel requestModel) {
//        return invoicesService.addManualInvoices(requestModel);
//    }
//
//}
