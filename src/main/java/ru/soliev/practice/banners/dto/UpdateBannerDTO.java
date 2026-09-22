package ru.soliev.practice.banners.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

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

    public UpdateBannerDTO() {

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
