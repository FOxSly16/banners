package ru.soliev.practice.banners.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.thymeleaf.expression.Lists;
import ru.soliev.practice.banners.exceptions.CategoryNotDeletedException;
import ru.soliev.practice.banners.exceptions.CategoryNotFoundException;

import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.models.Category;
import ru.soliev.practice.banners.repositories.BannerRepository;
import ru.soliev.practice.banners.repositories.CategoryRepository;


import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BannerRepository bannerRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, BannerRepository bannerRepository) {
        this.categoryRepository = categoryRepository;
        this.bannerRepository = bannerRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findByDeletedFalse();
    }

    @Transactional(readOnly = true)
    public Category findByNameExceptThisId(String name, int id) {
        return categoryRepository.findByNameAndIdNot(name, id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Category findByReqNameExceptThisId(String name, int id) {
        return categoryRepository.findByReqNameAndIdNot(name, id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Category findById(int id) throws CategoryNotFoundException {
        return categoryRepository.findByIdAndDeletedFalse(id).orElseThrow(()
                -> new CategoryNotFoundException("Category with id " + id + " was not found"));
    }

    @Transactional(readOnly = true)
    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public Category findByReqName(String reqName) {
        return categoryRepository.findByReqName(reqName).orElse(null);
    }

    @Transactional(readOnly = true)
    public Category findByReqNameAndDeletedFalse(String reqName) throws CategoryNotFoundException {
        return categoryRepository.findByReqNameAndDeletedFalse(reqName).orElseThrow(()
                -> new CategoryNotFoundException("Category with reqName " + reqName + " was not found"));
    }
    public void update(int id, Category updatedCategory) {
        updatedCategory.setId(id);
        categoryRepository.save(updatedCategory);
    }

    @Transactional
    public List<Banner> delete(int id) throws CategoryNotFoundException {
        Category category = categoryRepository.findByIdAndDeletedFalse(id).
                orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " was not found"));

        if (bannerRepository.existsByCategory_IdAndDeletedFalse(id)) {
            return bannerRepository.findByCategory_IdAndDeletedFalse(id);
        }

        category.setDeleted(true);
        return List.of();
    }

    @Transactional(readOnly = true)
    public List<Category> search(String query) throws CategoryNotFoundException {
        if (categoryRepository.findByQuery(query).isEmpty()) {
            throw new CategoryNotFoundException("Category start with " + query + " was not found");
        }
        return categoryRepository.findByQuery(query);
    }

    public Category findByBannerId(int bannerId) throws CategoryNotFoundException {
        return categoryRepository.findByBanners_Id(bannerId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with bannerId = " + bannerId + " was not found"));
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

}
