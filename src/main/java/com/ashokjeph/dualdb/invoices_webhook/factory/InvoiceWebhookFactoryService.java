package com.ashokjeph.dualdb.invoices_webhook.factory;

import com.ashokjeph.dualdb.invoices_webhook.AzureBlobStorageService;
import com.ashokjeph.dualdb.repositories.secondary_repository.*;

public interface InvoiceWebhookFactoryService {

    InvoicesRepository getInvoicesRepository();
    MerchantBuyerPlatformsRepository getMerchantBuyerPlatformsRepository();
    MerchantRepository getMerchantRepository();
    CurrencyMasterRepository getCurrencyMasterRepository();
    AmazonVCInvoiceRepository getAmazonVcInvoiceRepository();
    VendorRpaInvoiceRepository getVendorRpaInvoiceRepository();
    AzureBlobStorageService getAzureBlobStorageService();
}
