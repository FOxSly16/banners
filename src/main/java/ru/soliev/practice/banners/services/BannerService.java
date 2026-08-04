package ru.soliev.practice.banners.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.soliev.practice.banners.exceptions.BannerNotFoundException;
import ru.soliev.practice.banners.exceptions.CategoryNotFoundException;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.models.Category;
import ru.soliev.practice.banners.repositories.BannerRepository;
import ru.soliev.practice.banners.repositories.CategoryRepository;

import java.util.Collections;
import java.util.List;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final CategoryService categoryService;

    @Autowired
    public BannerService(BannerRepository bannerRepository, CategoryService categoryService) {
        this.bannerRepository = bannerRepository;
        this.categoryService = categoryService;
    }

    @Transactional(readOnly = true)
    public List<Banner> findAll() {
        return bannerRepository.findByDeletedFalse();
    }

    @Transactional(readOnly = true)
    public Banner findByNameExceptThisId(String name, int id) {
        return bannerRepository.findByNameAndIdNot(name, id).orElse(null);
    }


    @Transactional(readOnly = true)
    public Banner findById(int id) throws BannerNotFoundException {
        return bannerRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new BannerNotFoundException(String.valueOf(id)));
    }

    @Transactional(readOnly = true)
    public Banner findByName(String name) {
        return bannerRepository.findByName(name).orElse(null);
    }

    @Transactional
    public void update(int id, Banner updatedBanner) throws CategoryNotFoundException {
        updatedBanner.setId(id);
        assign(updatedBanner, updatedBanner.getCategory().getId());
    }

    @Transactional
    public void delete(int id) throws BannerNotFoundException {
        Banner banner = bannerRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new BannerNotFoundException(String.valueOf("id")));
        banner.setDeleted(true);
    }

    @Transactional(readOnly = true)
    public List<Banner> search(String query) {
        return bannerRepository.findByQuery(query);
    }

    public void save(Banner banner) throws CategoryNotFoundException {
        assign(banner, banner.getCategory().getId());
    }

    public void assign(Banner banner, int categoryId) throws CategoryNotFoundException {
        Category category = categoryService.findById(categoryId);
        category.getBanners().add(banner);

        banner.setCategory(category);
        bannerRepository.save(banner);
    }

    public List<Banner> findByCategoryIdOrderedDesc(int id) {
        return bannerRepository.findByCategory_IdAndDeletedFalseOrderByPriceDesc(id);
    }
}
