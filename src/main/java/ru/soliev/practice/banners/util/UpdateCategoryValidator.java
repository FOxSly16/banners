package ru.soliev.practice.banners.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.soliev.practice.banners.models.Category;
import ru.soliev.practice.banners.services.CategoryService;

@RequiredArgsConstructor
@Component
public class UpdateCategoryValidator implements Validator {

    private final CategoryService categoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Category updatedCategory = (Category) target;

        if (categoryService.findByNameExceptThisId(updatedCategory.getName(), updatedCategory.getId()) != null) {
            errors.rejectValue("name", "", "This name is already taken");
        }

        if (categoryService.findByReqNameExceptThisId(updatedCategory.getReqName(), updatedCategory.getId()) != null) {
            errors.rejectValue("reqName", "", "This reqName is already taken");
        }
    }
}
