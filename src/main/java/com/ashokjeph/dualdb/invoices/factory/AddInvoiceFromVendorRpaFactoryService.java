package com.ashokjeph.dualdb.invoices.factory;

import com.ashokjeph.dualdb.repositories.secondary_repository.FactParamRepository;
import com.ashokjeph.dualdb.repositories.secondary_repository.InvoicesFileStoreRepository;
import com.ashokjeph.dualdb.repositories.secondary_repository.InvoicesRepository;
import com.ashokjeph.dualdb.repositories.secondary_repository.VendorRpaInvoiceRepository;

public interface AddInvoiceFromVendorRpaFactoryService {

    InvoicesRepository getInvoicesRepository();

    VendorRpaInvoiceRepository getVendorRpaInvoiceRepository();

    InvoicesFileStoreRepository getInvoicesFileStoreRepository();

    FactParamRepository getFactParamRepository();
}
