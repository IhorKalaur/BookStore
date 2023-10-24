package com.example.bookstore.dto.order;

import com.example.bookstore.model.Order;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateOrderStatusRequestDto {
    @NotBlank(message = "required field")
    private Order.Status status;
}
