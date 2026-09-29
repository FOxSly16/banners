package ru.soliev.practice.banners.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UpdateBannerDTO {

    @Size(min = 1)
    private String name;

    @Min(value = 1, message = "Price must be greater then 0")
    @Max(value = 100_000, message = "Price must be less then 100_000")
    private BigDecimal price;

    @Size(min = 1)
    private String categoryName;

    @Size(min = 1)
    private String content;

}
