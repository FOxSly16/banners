package ru.soliev.practice.banners.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.services.BannerService;

@RequiredArgsConstructor
@Component
public class UpdateBannerValidator implements Validator {

    private final BannerService bannerService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Banner.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Banner updatedBanner = (Banner) target;

        if (bannerService.findByNameExceptThisId(updatedBanner.getName(), updatedBanner.getId()) != null) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }

    public void validate(int bannerId, String bannerName, Errors errors) {
        if (bannerService.findByNameExceptThisId(bannerName, bannerId) != null) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}
