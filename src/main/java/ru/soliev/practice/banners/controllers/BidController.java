package ru.soliev.practice.banners.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.models.Request;
import ru.soliev.practice.banners.services.BannerService;
import ru.soliev.practice.banners.services.CategoryService;
import ru.soliev.practice.banners.services.RequestService;

import java.util.List;

@Controller
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
    public String getBannerTextByCategoryReqName(@RequestParam (value = "category", required = false) String reqName,
                                                 Model model, HttpServletRequest httpServletRequest, HttpServletResponse response) {

        Request request = requestService.createRequest(httpServletRequest);

        if (reqName != null) {

            List<Banner> banners = bannerService.findByCategoryIdOrderedDesc(categoryService.findByReqName(reqName).getId());
            Banner banner = requestService.showBanner(banners, request);

            if (!banners.isEmpty() && banner != null) {

                model.addAttribute("bannerText", banner.getContent());
                request.setBanner(banner);

            } else {

                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                requestService.save(request);
                return null;
            }
        }

        requestService.save(request);
        return "bid";
    }
}
