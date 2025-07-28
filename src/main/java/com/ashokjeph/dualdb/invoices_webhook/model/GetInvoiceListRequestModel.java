package com.ashokjeph.dualdb.invoices_webhook.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GetInvoiceListRequestModel {

    @Min(value = 0, message = "Page index must be zero or greater")
    private int page;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    private int size;

    @NotBlank(message = "Sort parameter is required")
    private String sort;

    @NotNull(message = "From date is required")
    private LocalDateTime fromDate;

    @NotNull(message = "To date is required")
    private LocalDateTime toDate;
    // Getters and Setters

}
