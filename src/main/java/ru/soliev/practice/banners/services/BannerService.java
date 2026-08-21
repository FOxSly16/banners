package ru.soliev.practice.banners.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.soliev.practice.banners.exceptions.BannerNotFoundException;
import ru.soliev.practice.banners.exceptions.CategoryNotFoundException;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.models.Category;
import ru.soliev.practice.banners.repositories.BannerRepository;
import ru.soliev.practice.banners.repositories.CategoryRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        return bannerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BannerNotFoundException("Banner with id " + id + " was not found"));
    }

    @Transactional(readOnly = true)
    public Banner findByName(String name) {
        return bannerRepository.findByName(name).orElse(null);
    }

    @Transactional
    public void update(int id, Banner updatedBanner, int categoryId) throws CategoryNotFoundException {
        updatedBanner.setId(id);
        addBanner(updatedBanner, categoryId);
    }

    @Transactional
    public void delete(int id) throws BannerNotFoundException {
        Banner banner = bannerRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new BannerNotFoundException("Banner with id " + id + " was not found"));
        banner.setDeleted(true);
    }

    @Transactional(readOnly = true)
    public List<Banner> search(String query) throws BannerNotFoundException {
        if (bannerRepository.findByQuery(query).isEmpty())
            throw new BannerNotFoundException("Banners start with " + query + " was not found");
        return bannerRepository.findByQuery(query);
    }

    @Transactional
    public void save(Banner banner) throws CategoryNotFoundException {
        addBanner(banner, banner.getCategory().getId());
    }

    @Transactional
    public void addBanner(Banner banner, int categoryId) throws CategoryNotFoundException {
        Category category = categoryService.findById(categoryId);
        category.getBanners().add(banner);

        banner.setCategory(category);
        bannerRepository.save(banner);
    }

    @Transactional(readOnly = true)
    public List<Banner> findByCategoryIdOrderedDesc(int id) {
        return bannerRepository.findByCategory_IdAndDeletedFalseOrderByPriceDesc(id);
    }

    @Transactional(readOnly = true)
    public List<Banner> findByCategoryIdWithRequests(int categoryId) {
        return bannerRepository.findByCategoryIdWithRequests(categoryId, LocalDateTime.now().minusMinutes(2));
    }
}
