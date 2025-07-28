package com.ashokjeph.dualdb.invoices_webhook;

import com.azure.storage.blob.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AzureBlobStorageService {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

//    public String uploadFile(MultipartFile file, String fileName) {
//        try {
//            // Create blob client
//            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
//                    .connectionString(connectionString)
//                    .buildClient();
//
//            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
//
//            if (!containerClient.exists()) {
//                containerClient.create();
//            }
//
//            BlobClient blobClient = containerClient.getBlobClient(fileName);
//
//            blobClient.upload(file.getInputStream(), file.getSize(), true);
//
//            // Return the URL of the uploaded file
//            return blobClient.getBlobUrl();
//
//        } catch (Exception e) {
//            throw new RuntimeException("Azure upload failed", e);
//        }
//    }

    public String uploadInvoiceFile(MultipartFile file, Long buyerPlatformId, String vendorId, String invoiceNumber) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be null or empty.");
        }
        if (buyerPlatformId == null ) {
            throw new IllegalArgumentException("buyerPlatformId must not be null or empty.");
        }
        if (vendorId == null || vendorId.isBlank()) {
            throw new IllegalArgumentException("vendorId must not be null or empty.");
        }
        if (invoiceNumber == null || invoiceNumber.isBlank()) {
            throw new IllegalArgumentException("invoiceNumber must not be null or empty.");
        }

        try {
            // Step 1: Build blob service client
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();

            // Step 2: Get container client
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            if (!containerClient.exists()) {
                containerClient.create();
            }

            // Step 3: Construct blob path (virtual folders)
            String blobPath = String.format("invoice/%s/%s/%s.pdf", buyerPlatformId, vendorId, invoiceNumber);

            // Step 4: Get blob client
            BlobClient blobClient = containerClient.getBlobClient(blobPath);

            // Step 5: Check if file already exists (to prevent overwriting)
            if (blobClient.exists()) {
                throw new RuntimeException("Invoice file already exists for this vendor and invoice number.");
            }

            // Step 6: Upload the file
            blobClient.upload(file.getInputStream(), file.getSize(), true);

            // Step 7: Return the file URL
            return blobClient.getBlobUrl();

        } catch (IllegalArgumentException e) {
            throw e; // Rethrow input validation errors directly
        } catch (Exception e) {
            throw new RuntimeException("Azure Blob upload failed: " + e.getMessage(), e);
        }
    }

}

