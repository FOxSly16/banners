package ru.soliev.practice.banners.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.services.BannerService;

@Component
@RequiredArgsConstructor
public class CreateBannerValidator implements Validator {

    private final BannerService bannerService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Banner.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String name = (String) target;

        if (bannerService.findByName(name) != null) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}
