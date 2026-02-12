package com.order.mgmt.service.dto;

import com.order.mgmt.service.entities.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusRequest {
    @NotNull(message = "Status is mandatory")
    private OrderStatus orderStatus;
}
