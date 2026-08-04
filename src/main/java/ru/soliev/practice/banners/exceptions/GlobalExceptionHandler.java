package ru.soliev.practice.banners.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    public String handleCategoryNotFoundException(Model model, CategoryNotFoundException e) {
        model.addAttribute("msg", e.getMessage());
        return "errors/categoryError";
    }

    @ExceptionHandler(BannerNotFoundException.class)
    public String handleBannerNotFoundException(Model model, BannerNotFoundException e) {
        model.addAttribute("msg", e.getMessage());
        return "errors/bannerError";
    }
}
