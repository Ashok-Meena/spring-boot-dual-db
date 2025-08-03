package com.ashokjeph.dualdb.config.entity.secondary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "invoice_file")
@Data
@NoArgsConstructor
public class InvoiceFile implements Serializable {
	@Serial
    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 50)
    private String id;

    @Column(nullable = false, unique = true)
    private String invoiceFileName;

    private String invoiceFileType;
//    private String invoiceFilePath;
    private String blobFilePath;
    
    @Lob
    @JsonIgnore
    @ToString.Exclude
    private byte[] data;

    public InvoiceFile(String invoiceFileName, String invoiceFileType) {
        this.invoiceFileName = invoiceFileName;
        this.invoiceFileType = invoiceFileType;
    }
}