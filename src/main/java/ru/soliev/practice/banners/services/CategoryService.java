package ru.soliev.practice.banners.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.soliev.practice.banners.exceptions.CategoryNotFoundException;

import ru.soliev.practice.banners.models.Category;
import ru.soliev.practice.banners.repositories.CategoryRepository;


import java.util.List;


@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
        return categoryRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new CategoryNotFoundException(String.valueOf(id)));
    }

    @Transactional(readOnly = true)
    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public Category findByReqName(String reqName) {
        return categoryRepository.findByReqName(reqName).orElse(null);
    }

    public void update(int id, Category updatedCategory) {
        updatedCategory.setId(id);
        categoryRepository.save(updatedCategory);
    }

    public void delete(int id) throws CategoryNotFoundException {
        Category category = categoryRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new CategoryNotFoundException(String.valueOf(id)));
        category.setDeleted(true);

    }

    @Transactional(readOnly = true)
    public List<Category> search(String query) {
        return categoryRepository.findByQuery(query);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

}
