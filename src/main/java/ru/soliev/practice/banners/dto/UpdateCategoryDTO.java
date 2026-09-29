package ru.soliev.practice.banners.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCategoryDTO {

    @Size(min = 1)
    private String name;

    @Size(min = 1)
    private String reqName;
}
