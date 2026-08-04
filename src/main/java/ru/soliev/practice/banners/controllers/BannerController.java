package ru.soliev.practice.banners.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.soliev.practice.banners.exceptions.BannerNotFoundException;
import ru.soliev.practice.banners.exceptions.CategoryNotFoundException;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.models.Category;
import ru.soliev.practice.banners.services.BannerService;
import ru.soliev.practice.banners.services.CategoryService;
import ru.soliev.practice.banners.util.CreateBannerValidator;
import ru.soliev.practice.banners.util.CreateCategoryValidator;
import ru.soliev.practice.banners.util.UpdateBannerValidator;
import ru.soliev.practice.banners.util.UpdateCategoryValidator;

@Controller
@RequestMapping("/banner")
public class BannerController {

    private final BannerService bannerService;
    private final CreateBannerValidator createBannerValidator;
    private final UpdateBannerValidator updateBannerValidator;
    private final CategoryService categoryService;

    public BannerController(BannerService bannerService, CreateBannerValidator createBannerValidator,
                            UpdateBannerValidator updateBannerValidator, CategoryService categoryService) {
        this.bannerService = bannerService;
        this.createBannerValidator = createBannerValidator;
        this.updateBannerValidator = updateBannerValidator;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(@RequestParam(value = "query", required = false) String query, Model model) {

        model.addAttribute("searched", false);

        if (query != null) {
            model.addAttribute("searched", true);
            model.addAttribute("foundedBanners", bannerService.search(query));
        } else
            model.addAttribute("banners", bannerService.findAll());

        return "banners/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable ("id") int id) throws BannerNotFoundException {

        model.addAttribute("banner", bannerService.findById(id));
        model.addAttribute("banners", bannerService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("bannerName", bannerService.findById(id).getName());

        return "banners/show";
    }

    @GetMapping("/new")
    public String newBanner(@ModelAttribute ("banner") Banner banner, Model model) {

        model.addAttribute("banners", bannerService.findAll());
        model.addAttribute("categories", categoryService.findAll());

        return "banners/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("banner") @Valid Banner banner, BindingResult bindingResult,
                         Model model) throws CategoryNotFoundException {

        createBannerValidator.validate(banner, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("banners", bannerService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            return "banners/new";
        }

        bannerService.save(banner);
        return "redirect:/banner";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute ("banner") @Valid Banner updatedBanner,
                         BindingResult bindingResult, Model model,
                         @PathVariable("id") int id) throws CategoryNotFoundException, BannerNotFoundException {

        updatedBanner.setId(id);

        updateBannerValidator.validate(updatedBanner, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("banners", bannerService.findAll());
            model.addAttribute("bannerName", bannerService.findById(id).getName());
            model.addAttribute("categories", categoryService.findAll());
            return "banners/show";
        }

        bannerService.update(id, updatedBanner);
        return "redirect:/banner";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) throws BannerNotFoundException {

        bannerService.delete(id);
        return "redirect:/banner";

    }



}
