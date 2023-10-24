package com.example.bookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateOrderShippingAddressRequestDto {
    @NotBlank(message = "required field")
    @Length(max = 255, message = "size must be less than 255 character")
    private String shippingAddress;
}
