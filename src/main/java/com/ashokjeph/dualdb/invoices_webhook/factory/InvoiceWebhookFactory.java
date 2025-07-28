package com.ashokjeph.dualdb.invoices_webhook.factory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ashokjeph.dualdb.entity.secontary.Invoices;
import com.ashokjeph.dualdb.entity.secontary.VendorBuyerPlatformId;
import com.ashokjeph.dualdb.entity.secontary.VendorRpaInvoicesEntity;
import com.ashokjeph.dualdb.exception.ApiError;
import com.ashokjeph.dualdb.invoices_webhook.model.InvoiceWebhookRequestModel;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

//@RequiredArgsConstructor // for create automatically constructor and assign value to all instance
public class InvoiceWebhookFactory {

    private final InvoiceWebhookFactoryService factoryService;

    public InvoiceWebhookFactory(InvoiceWebhookFactoryService factoryService){
        this.factoryService=factoryService;
    }

    public ResponseEntity<?> processInvoiceWebhook(InvoiceWebhookRequestModel requestModel) {
        try {
            List<Map<String, String>> validationErrors = validate(requestModel, factoryService);

            if (!validationErrors.isEmpty()) {
                Map<String, Object> errorBody = new HashMap<>();
                errorBody.put("success", false);

                Map<String, Object> errorDetails = new HashMap<>();
                errorDetails.put("code", "INVALID_PAYLOAD");
                errorDetails.put("message", "The request payload contains invalid data");
                errorDetails.put("details", validationErrors);

                errorBody.put("error", errorDetails);
                errorBody.put("requestId", UUID.randomUUID().toString());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
            }

            // Proceed with invoice processing...
            VendorRpaInvoicesEntity rpaInvoicesEntity = factoryService.getVendorRpaInvoiceRepository()
                    .findByVendorBuyerPlatformId_BuyerPlatformIdAndVendorBuyerPlatformId_VendorIdAndInvoiceNumber(requestModel.getBuyerPlatformId(), requestModel.getVendorId(), requestModel.getInvoiceNumber())
                    .orElse(new VendorRpaInvoicesEntity());

            if (!rpaInvoicesEntity.getInvoiceNumber().trim().equalsIgnoreCase(requestModel.getInvoiceNumber().trim())) {
                rpaInvoicesEntity = new VendorRpaInvoicesEntity();
            }

            rpaInvoicesEntity.getVendorBuyerPlatformId().setBuyerPlatformId(requestModel.getBuyerPlatformId());
            rpaInvoicesEntity.getVendorBuyerPlatformId().setVendorId(requestModel.getVendorId());
            rpaInvoicesEntity.setInvoiceNumber(requestModel.getInvoiceNumber());
            rpaInvoicesEntity.setInvoiceAmount(requestModel.getInvoiceAmount());
            rpaInvoicesEntity.setInvoiceCurrency(requestModel.getInvoiceCurrency());
            rpaInvoicesEntity.setInvoiceCreationDate(requestModel.getInvoiceCreationDt());
            rpaInvoicesEntity.setInvoiceDueDate(requestModel.getInvoiceDueDt());
            rpaInvoicesEntity.setPoNumber(requestModel.getPoNumber());
            rpaInvoicesEntity.setInvoiceStatus(requestModel.getInvoiceStatus());
            rpaInvoicesEntity.setPayeeCode(requestModel.getPayeeCode() == null ? "" : requestModel.getPayeeCode());
            rpaInvoicesEntity.setCountry(requestModel.getCountry());
            rpaInvoicesEntity.setInvoiceAdded(false);

            // Handle invoice PDF or URL
            try {
                String invoiceType = requestModel.getInvoiceFileType();
                MultipartFile invoicesPdf = requestModel.getInvoiceFileValue();
                if ("url".equalsIgnoreCase(invoiceType)) {
                    String fileUrl = new String(invoicesPdf.getBytes(), StandardCharsets.UTF_8);
                    rpaInvoicesEntity.setInvoiceFilePath(fileUrl);
                } else if ("file".equalsIgnoreCase(invoiceType)) {
                    String fileName = UUID.randomUUID() + "_" + invoicesPdf.getOriginalFilename();
                    String uploadedUrl = factoryService.getAzureBlobStorageService().uploadInvoiceFile(requestModel.getInvoiceFileValue(), rpaInvoicesEntity.getVendorBuyerPlatformId().getBuyerPlatformId(), rpaInvoicesEntity.getVendorBuyerPlatformId().getVendorId(), requestModel.getInvoiceNumber());
                    rpaInvoicesEntity.setInvoiceFilePath(uploadedUrl);
                } else {
                    throw new IllegalArgumentException("Invalid invoiceFileType: must be either 'url' or 'file'");
                }
            } catch (Exception e) {
//                log.error("Failed to process invoice file", e);
                throw new RuntimeException("Invoice upload failed", e);  // or handle appropriately
            }

            // Set the value's invoice in the Invoices table
            Optional<Invoices> existingInvoiceOpt = factoryService.getInvoicesRepository().findTopByInvoiceNumber(rpaInvoicesEntity.getInvoiceNumber());
            if(existingInvoiceOpt.isPresent()) rpaInvoicesEntity.setInvoiceAdded(true);

            rpaInvoicesEntity.setCreationDate(LocalDateTime.now());
            factoryService.getVendorRpaInvoiceRepository().save(rpaInvoicesEntity);

//            return ResponseEntity.ok().body(Collections.singletonMap("success", true));
            return ApiError.getApiResponse(true, "Invoice Data Saved ", rpaInvoicesEntity, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("success", false);

            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("code", "INTERNAL_ERROR");
            errorDetails.put("message", "An unexpected error occurred while processing the webhook");
            errorDetails.put("referenceId", "err-" + UUID.randomUUID());

            errorBody.put("error", errorDetails);
            errorBody.put("requestId", UUID.randomUUID().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    public static List<Map<String, String>> validate(InvoiceWebhookRequestModel requestModel, InvoiceWebhookFactoryService factoryService) {
        List<Map<String, String>> errors = new ArrayList<>();

        //Check for buyer platform
        if (requestModel.getBuyerPlatformId() == null) {
            errors.add(error("buyer_platform_id", "Missing field"));
        } else {
            VendorBuyerPlatformId id = new VendorBuyerPlatformId();
            id.setBuyerPlatformId(requestModel.getBuyerPlatformId());
            id.setVendorId(requestModel.getVendorId());

            if (!factoryService.getMerchantBuyerPlatformsRepository().existsById(id)) {
                errors.add(error("buyer_platform_id", "Id not found"));
            }
        }

        //Check for merchant(vendor)
        if (requestModel.getVendorId() == null) {
            errors.add(error("vendor_id", "Missing field"));
        } else if (!factoryService.getMerchantRepository().existsById(requestModel.getVendorId())) {
            errors.add(error("vendor_id", "Id not found"));
        }

        //Check for invoice currency
        if (requestModel.getInvoiceCurrency() == null) {
            errors.add(error("invoice_currency", "Missing field"));
        } else if (!factoryService.getCurrencyMasterRepository().existsByCurrencyCode(requestModel.getInvoiceCurrency())) {
            errors.add(error("invoice_currency", "Invalid currency code"));
        }

        //Check for invoice creation date
        if (requestModel.getInvoiceCreationDt() == null) {
            errors.add(error("invoice_creation_dt", "Missing field"));
        } else if (requestModel.getInvoiceCreationDt().isAfter(LocalDateTime.now())) {
            errors.add(error("invoice_creation_dt", "Date can't be in future"));
        }

        //Check for invoice due date
        if (requestModel.getInvoiceDueDt() == null) {
            errors.add(error("invoice_due_dt", "Missing field"));
        }

        //Check for invoice type
        if (!Arrays.asList("file", "url").contains(requestModel.getInvoiceFileType())) {
            errors.add(error("invoice_file_type", "Invalid value. Provide 'file' or 'url'"));
        }

        return errors;
    }

    private static Map<String, String> error(String field, String issue) {
        Map<String, String> map = new HashMap<>();
        map.put("field", field);
        map.put("issue", issue);
        return map;
    }

}
