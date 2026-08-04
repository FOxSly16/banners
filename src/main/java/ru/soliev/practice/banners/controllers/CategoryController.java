package ru.soliev.practice.banners.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.soliev.practice.banners.exceptions.CategoryNotFoundException;
import ru.soliev.practice.banners.models.Category;
import ru.soliev.practice.banners.services.CategoryService;
import ru.soliev.practice.banners.util.CreateCategoryValidator;
import ru.soliev.practice.banners.util.UpdateCategoryValidator;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CreateCategoryValidator createCategoryValidator;
    private final UpdateCategoryValidator updateCategoryValidator;

    @Autowired
    public CategoryController(CategoryService categoryService, CreateCategoryValidator createCategoryValidator, UpdateCategoryValidator updateCategoryValidator) {
        this.categoryService = categoryService;
        this.createCategoryValidator = createCategoryValidator;
        this.updateCategoryValidator = updateCategoryValidator;
    }

    @GetMapping
    public String index(@RequestParam(value = "query", required = false) String query, Model model) {

        model.addAttribute("searched", false);

        if (query != null) {
            model.addAttribute("searched", true);
            model.addAttribute("foundedCategories", categoryService.search(query));
        } else
            model.addAttribute("categories", categoryService.findAll());

        return "categories/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable ("id") int id) throws CategoryNotFoundException {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("categoryName", categoryService.findById(id).getName());
        return "categories/show";
    }

    @GetMapping("/new")
    public String newCategory(@ModelAttribute ("category") Category category, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult, Model model) {

        createCategoryValidator.validate(category, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "categories/new";
        }

        categoryService.save(category);
        return "redirect:/category";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute ("category") @Valid Category updatedCategory,
                         BindingResult bindingResult, Model model,
                         @PathVariable("id") int id) throws CategoryNotFoundException {

        updatedCategory.setId(id);

        updateCategoryValidator.validate(updatedCategory, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("categoryName", categoryService.findById(id).getName());
            return "categories/show";
        }

        categoryService.update(id, updatedCategory);
        return "redirect:/category";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, Model model) throws CategoryNotFoundException {

        Category category = categoryService.findById(id);
        if (!category.getBanners().stream().filter(banner -> !banner.isDeleted()).toList().isEmpty()) {

            model.addAttribute("category", category);
            model.addAttribute("tryToDelete", true);
            model.addAttribute("bannersTryToDelete", category.getBanners());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("categoryName", category.getName());
            return "categories/show";
        }

        categoryService.delete(id);
        return "redirect:/category";
    }

}
