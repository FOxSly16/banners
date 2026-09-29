package ru.soliev.practice.banners.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.soliev.practice.banners.models.Category;
import ru.soliev.practice.banners.services.CategoryService;

@RequiredArgsConstructor
@Component
public class CreateCategoryValidator implements Validator {

    private final CategoryService categoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Category category = (Category) target;

        if (categoryService.findByNameForValidate(category.getName()) != null) {
            errors.rejectValue("name", "", "This name is already taken");
        }

        if (categoryService.findByReqName(category.getReqName()) != null) {
            errors.rejectValue("reqName", "", "This reqName is already taken");
        }
    }
}
