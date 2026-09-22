package ru.soliev.practice.banners.dto;

import jakarta.validation.constraints.Size;

public class UpdateCategoryDTO {

    @Size(min = 1)
    private String name;

    @Size(min = 1)
    private String reqName;


    public UpdateCategoryDTO() {

    }

    public UpdateCategoryDTO(String name, String reqName) {
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
