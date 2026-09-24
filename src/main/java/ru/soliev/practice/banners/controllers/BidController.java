package ru.soliev.practice.banners.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.soliev.practice.banners.dto.BannerDTO;
import ru.soliev.practice.banners.exceptions.CategoryNotFoundException;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.models.Category;
import ru.soliev.practice.banners.models.Request;
import ru.soliev.practice.banners.services.BannerService;
import ru.soliev.practice.banners.services.CategoryService;
import ru.soliev.practice.banners.services.RequestService;

import java.util.List;

@RestController
@RequestMapping("/bid")
public class BidController {

    private final CategoryService categoryService;
    private final BannerService bannerService;
    private final RequestService requestService;

    @Autowired
    public BidController(CategoryService categoryService, BannerService bannerService, RequestService requestService) {
        this.categoryService = categoryService;
        this.bannerService = bannerService;
        this.requestService = requestService;
    }

    @GetMapping()
    public ResponseEntity<String> getBannerTextByCategoryReqName(
            @RequestParam (value = "category", required = false) String reqName,
            HttpServletRequest httpServletRequest) throws CategoryNotFoundException {

        Request request = requestService.createRequest(httpServletRequest);

        if (reqName != null) {

            List<Banner> banners =
                    bannerService.findByCategoryIdWithRequests(categoryService.findByReqNameAndDeletedFalse(reqName).getId());
            Banner banner = requestService.showBanner(banners);

            if (!banners.isEmpty() && banner != null) {

                request.setBanner(banner);
                requestService.save(request);
                return new ResponseEntity<>(banner.getContent(), HttpStatus.OK);

            }

        }

        requestService.save(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
