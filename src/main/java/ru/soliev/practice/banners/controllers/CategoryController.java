package ru.soliev.practice.banners.controllers;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.soliev.practice.banners.Mappers.CategoryMapper;
import ru.soliev.practice.banners.dto.CategoryDTO;
import ru.soliev.practice.banners.exceptions.CategoryNotCreatedException;
import ru.soliev.practice.banners.exceptions.CategoryNotFoundException;
import ru.soliev.practice.banners.exceptions.CategoryNotUpdatedException;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.models.Category;
import ru.soliev.practice.banners.services.CategoryService;
import ru.soliev.practice.banners.util.CreateCategoryValidator;
import ru.soliev.practice.banners.util.UpdateCategoryValidator;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CreateCategoryValidator createCategoryValidator;
    private final UpdateCategoryValidator updateCategoryValidator;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, CreateCategoryValidator createCategoryValidator, UpdateCategoryValidator updateCategoryValidator, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.createCategoryValidator = createCategoryValidator;
        this.updateCategoryValidator = updateCategoryValidator;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public List<CategoryDTO> index(@RequestParam(value = "query", required = false) String query) throws CategoryNotFoundException {

        if (query != null)
            return categoryMapper.toCategoryDTOList(categoryService.search(query));
        return categoryMapper.toCategoryDTOList(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public CategoryDTO show(@PathVariable ("id") int id) throws CategoryNotFoundException {
        return categoryMapper.toCategoryDTO(categoryService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Integer> create(@RequestBody @Valid CategoryDTO categoryDTO, BindingResult bindingResult) throws CategoryNotCreatedException {

        Category category = categoryMapper.toEntity(categoryDTO);

        createCategoryValidator.validate(category, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new CategoryNotCreatedException(createErrorMsg(bindingResult));
        }

        categoryService.save(category);
        return new ResponseEntity<>(category.getId(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid CategoryDTO updatedCategoryDTO,
                                             BindingResult bindingResult,
                                             @PathVariable("id") int id) throws CategoryNotFoundException, CategoryNotUpdatedException {

        Category updatedCategory = categoryMapper.toEntity(updatedCategoryDTO);
        updatedCategory.setId(id);

        updateCategoryValidator.validate(updatedCategory, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new CategoryNotUpdatedException(createErrorMsg(bindingResult));
        }

        categoryService.update(id, updatedCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Integer>> delete(@PathVariable("id") int id) throws CategoryNotFoundException {

        List<Banner> banners = categoryService.delete(id);

        if (!banners.isEmpty())
            return new ResponseEntity<>(banners.stream().map(Banner::getId).toList(), HttpStatus.CONFLICT);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public String createErrorMsg(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();

        for (FieldError error : errors) {
            msg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
        }

        return msg.toString();
    }

}
