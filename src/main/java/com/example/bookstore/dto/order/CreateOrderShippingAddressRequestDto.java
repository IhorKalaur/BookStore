package com.example.bookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateOrderShippingAddressRequestDto {
    @NotBlank
    @Length(max = 255)
    private String shippingAddress;
}
