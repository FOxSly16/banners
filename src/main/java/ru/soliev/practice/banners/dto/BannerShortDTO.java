package ru.soliev.practice.banners.dto;

import java.math.BigDecimal;

public record BannerShortDTO(BigDecimal price, String name, String content) {
}
