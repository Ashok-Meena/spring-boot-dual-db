package com.ashokjeph.dualdb.invoices_webhook.factory;

import com.ashokjeph.dualdb.repositories.secondary_repository.VendorRpaInvoiceRepository;

public interface GetInvoicesListFactoryService {

    VendorRpaInvoiceRepository getVendorRpaInvoiceRepository();
}
