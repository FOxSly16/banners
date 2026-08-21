package ru.soliev.practice.banners.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import ru.soliev.practice.banners.models.Banner;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String reqName;

    //private List<BannerDTO> banners = new ArrayList<>();

    public CategoryDTO() {

    }

    public CategoryDTO( String name, String reqName) {
        this.name = name;
        this.reqName = reqName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }
}
