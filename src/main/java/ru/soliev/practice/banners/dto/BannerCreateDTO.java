package ru.soliev.practice.banners.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.soliev.practice.banners.controllers.BannerController;

import java.math.BigDecimal;

public class BannerCreateDTO {
    @NotBlank
    private String name;

    @NotNull(message = "Enter the price")
    @Min(value = 1, message = "Price must be greater then 0")
    @Max(value = 100_000, message = "Price must be less then 100_000")
    private BigDecimal price;

    @NotNull
    private String categoryName;

    @NotBlank
    private String content;

    public BannerCreateDTO() {

    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
