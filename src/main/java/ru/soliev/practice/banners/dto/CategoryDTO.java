package ru.soliev.practice.banners.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String reqName;

}
