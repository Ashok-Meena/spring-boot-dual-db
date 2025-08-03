//package com.ashokjeph.dualdb.contoller;
//
//import ai.funder.server.invoices_webhook.AzureBlobStorageService;
//import ai.funder.server.invoices_webhook.InvoicesWebhookService;
//import ai.funder.server.invoices_webhook.model.InvoiceWebhookRequestModel;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/webhooks/v1")
//@RequiredArgsConstructor
//public class InvoiceWebhookController {
//
//    @Autowired
//    private InvoicesWebhookService invoiceWebhookService;
//
//    @Autowired
//    private AzureBlobStorageService azureBlobStorageService;
//
//    @PostMapping(value = "/invoices", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> receiveInvoiceWebhook(@ModelAttribute @Valid InvoiceWebhookRequestModel requestModel) {
//        return invoiceWebhookService.processInvoiceWebhook(requestModel);
//    }
//
//    @PostMapping("/upload-invoice")
//    public ResponseEntity<?> uploadInvoiceFile(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("buyerPlatformId") Long buyerPlatformId,
//            @RequestParam("vendorId") String vendorId,
//            @RequestParam("invoiceNumber") String invoiceNumber) {
//        try {
//            String blobUrl = azureBlobStorageService.uploadInvoiceFile(file, buyerPlatformId, vendorId, invoiceNumber);
//            return ResponseEntity.ok().body("File uploaded successfully. URL: " + blobUrl);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
//        }
//    }
//}
