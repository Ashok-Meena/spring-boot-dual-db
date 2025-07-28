package com.ashokjeph.dualdb.invoices_webhook.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ashokjeph.dualdb.entity.secontary.VendorRpaInvoicesEntity;
import com.ashokjeph.dualdb.exception.ApiError;
import com.ashokjeph.dualdb.exception.VendorInvoiceNotFoundException;
import com.ashokjeph.dualdb.invoices_webhook.model.GetInvoiceListRequestModel;
import com.ashokjeph.dualdb.invoices_webhook.model.GetInvoiceListResponseModel;

@Slf4j
public class GetInvoicesListFactory {

    private final GetInvoicesListFactoryService factoryService;

    public GetInvoicesListFactory(GetInvoicesListFactoryService factoryService){
        this.factoryService = factoryService;
    }

    public ResponseEntity<?> getInvoicesList(GetInvoiceListRequestModel requestModel) {
        log.debug("Fetching invoices with request: {}", requestModel);
        Pageable pageable = PageRequest.of(
                requestModel.getPage(),
                requestModel.getSize(),
                Sort.by(requestModel.getSort())
        );

        Page<VendorRpaInvoicesEntity> invoiceEntities = factoryService
                .getVendorRpaInvoiceRepository()
                .findInvoices(requestModel.getFromDate(), requestModel.getToDate(), pageable);

        if(invoiceEntities == null || invoiceEntities.isEmpty()){
            log.warn("No invoices found for the specified criteria: {}", requestModel);
            throw new VendorInvoiceNotFoundException("No invoices found for the specified criteria.", HttpStatus.NOT_FOUND);
        }

        Page<GetInvoiceListResponseModel> responsePage = invoiceEntities.map(invoice -> {
            GetInvoiceListResponseModel responseModel = new GetInvoiceListResponseModel();
            responseModel.setId(invoice.getId());
            responseModel.setInvoiceNumber(invoice.getInvoiceNumber());
            responseModel.setInvoiceAmount(invoice.getInvoiceAmount());
            responseModel.setInvoiceCurrency(invoice.getInvoiceCurrency());
            responseModel.setInvoiceDate(invoice.getInvoiceCreationDate());
            responseModel.setInvoiceStatus(invoice.getInvoiceStatus());
            return responseModel;
        });

        log.info("Successfully retrieved {} invoices.", responsePage.getTotalElements());
        return ApiError.getApiResponse(true, "Invoices retrieved successfully.", responsePage, HttpStatus.OK);
    }

}
