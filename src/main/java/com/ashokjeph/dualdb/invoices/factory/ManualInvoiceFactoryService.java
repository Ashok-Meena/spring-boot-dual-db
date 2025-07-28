package com.ashokjeph.dualdb.invoices.factory;

import com.ashokjeph.dualdb.repositories.secondary_repository.InvoicesRepository;

public interface ManualInvoiceFactoryService {

    InvoicesRepository getInvoicesRepository();
}
