package com.ashokjeph.dualdb.repositories.secondary_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ashokjeph.dualdb.entity.secontary.InvoiceFile;

import java.util.Optional;

@RepositoryRestResource(exported=false,collectionResourceRel = "invoicefile", path = "invoicefile")
public interface InvoicesFileStoreRepository extends JpaRepository<InvoiceFile, String>  {
	Optional<InvoiceFile> findByInvoiceFileName(String invoiceFileName);
}
