package com.order.mgmt.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotBlank(message = "Customer name is mandatory")
    private String customerName;

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be greater than 0")
    private BigDecimal amount;

   }
