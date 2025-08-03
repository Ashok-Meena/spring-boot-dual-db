package com.ashokjeph.dualdb.repository.secondary;

import com.ashokjeph.dualdb.config.entity.secondary.InvoiceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported=false,collectionResourceRel = "invoicefile", path = "invoicefile")
public interface InvoicesFileStoreRepository extends JpaRepository<InvoiceFile, String>  {
	Optional<InvoiceFile> findByInvoiceFileName(String invoiceFileName);
}
