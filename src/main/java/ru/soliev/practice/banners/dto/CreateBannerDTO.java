package ru.soliev.practice.banners.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreateBannerDTO {
    @NotBlank(message = "Should be not empty")
    private String name;

    @NotNull(message = "Enter the price")
    @Min(value = 1, message = "Price should be greater then 0")
    @Max(value = 100_000, message = "Price should be less then 100_000")
    private BigDecimal price;

    @NotBlank(message = "Should be not empty")
    private String categoryName;

    @NotBlank(message = "Should be not empty")
    private String content;

}
