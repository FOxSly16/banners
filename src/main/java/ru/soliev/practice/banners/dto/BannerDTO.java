package ru.soliev.practice.banners.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BannerDTO {
    @NotBlank
    private String name;

    @NotNull(message = "Enter the price")
    @Min(value = 1, message = "Price must be greater then 0")
    @Max(value = 100_000, message = "Price must be less then 100_000")
    private BigDecimal price;

    @NotNull
    private Integer categoryId;

    @NotBlank
    private String content;
}
