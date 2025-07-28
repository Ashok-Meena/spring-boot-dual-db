package com.ashokjeph.dualdb.invoices_webhook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ashokjeph.dualdb.invoices_webhook.factory.InvoiceWebhookFactory;
import com.ashokjeph.dualdb.invoices_webhook.factory.InvoiceWebhookFactoryService;
import com.ashokjeph.dualdb.invoices_webhook.model.InvoiceWebhookRequestModel;
import com.ashokjeph.dualdb.repositories.secondary_repository.*;

@Service
public class InvoicesWebhookService implements InvoiceWebhookFactoryService {

    @Autowired
    private AzureBlobStorageService azureBlobStorageService;

    @Autowired
    private InvoicesRepository invoicesRepository;

    @Autowired
    private MerchantBuyerPlatformsRepository merchantBuyerPlatformsRepository;

    @Autowired
    private CurrencyMasterRepository currencyMasterRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private AmazonVCInvoiceRepository amazonVCInvoiceRepository;

    @Autowired
    private VendorRpaInvoiceRepository vendorRpaInvoiceRepository;

    @Override
    public InvoicesRepository getInvoicesRepository() {
        return this.invoicesRepository;
    }

    @Override
    public MerchantBuyerPlatformsRepository getMerchantBuyerPlatformsRepository() {
        return this.merchantBuyerPlatformsRepository;
    }

    @Override
    public CurrencyMasterRepository getCurrencyMasterRepository() {
        return this.currencyMasterRepository;
    }

    @Override
    public MerchantRepository getMerchantRepository() {
        return this.merchantRepository;
    }

    @Override
    public AmazonVCInvoiceRepository getAmazonVcInvoiceRepository(){
        return this.amazonVCInvoiceRepository;
    }

    @Override
    public VendorRpaInvoiceRepository getVendorRpaInvoiceRepository() {
        return this.vendorRpaInvoiceRepository;
    }

    @Override
    public AzureBlobStorageService getAzureBlobStorageService() {
        return this.azureBlobStorageService;
    }

    public ResponseEntity<?> processInvoiceWebhook(InvoiceWebhookRequestModel requestModel) {
        InvoiceWebhookFactory invoiceWebhookFactory = new InvoiceWebhookFactory(this);
        return invoiceWebhookFactory.processInvoiceWebhook(requestModel);
    }
}


