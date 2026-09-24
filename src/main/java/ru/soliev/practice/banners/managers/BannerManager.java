package ru.soliev.practice.banners.managers;

import org.springframework.stereotype.Component;
import ru.soliev.practice.banners.Mappers.BannerMapper;
import ru.soliev.practice.banners.dto.BannerShortDTO;
import ru.soliev.practice.banners.dto.CreateBannerDTO;
import ru.soliev.practice.banners.exceptions.CategoryNotFoundException;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.models.Category;
import ru.soliev.practice.banners.services.BannerService;
import ru.soliev.practice.banners.services.CategoryService;

@Component
public class BannerManager {

    private final BannerService bannerService;
    private final CategoryService categoryService;
    private final BannerMapper bannerMapper;


    public BannerManager(BannerService bannerService, CategoryService categoryService, BannerMapper bannerMapper) {
        this.bannerService = bannerService;
        this.categoryService = categoryService;
        this.bannerMapper = bannerMapper;
    }

    public Banner createBanner(CreateBannerDTO createBannerDTO) throws CategoryNotFoundException {

        Banner banner = bannerMapper.toEntity(createBannerDTO);
        Category category = categoryService.findByName(createBannerDTO.getCategoryName());

        banner.setCategory(category);
        bannerService.saveBanner(banner);

        return banner;
    }
}
