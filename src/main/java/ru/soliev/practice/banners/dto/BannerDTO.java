package ru.soliev.practice.banners.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

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

    public BannerDTO() {

    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
