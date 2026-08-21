package ru.soliev.practice.banners.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.soliev.practice.banners.Mappers.BannerMapper;;
import ru.soliev.practice.banners.dto.BannerCreateDTO;
import ru.soliev.practice.banners.dto.BannerShortDTO;
import ru.soliev.practice.banners.dto.BannerDTO;
import ru.soliev.practice.banners.exceptions.BannerNotCreatedException;
import ru.soliev.practice.banners.exceptions.BannerNotFoundException;
import ru.soliev.practice.banners.exceptions.BannerNotUpdatedException;
import ru.soliev.practice.banners.exceptions.CategoryNotFoundException;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.models.Category;
import ru.soliev.practice.banners.services.BannerService;
import ru.soliev.practice.banners.services.CategoryService;
import ru.soliev.practice.banners.util.CreateBannerValidator;
import ru.soliev.practice.banners.util.UpdateBannerValidator;

import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {

    private final BannerService bannerService;
    private final CreateBannerValidator createBannerValidator;
    private final UpdateBannerValidator updateBannerValidator;
    private final BannerMapper bannerMapper;
    private final CategoryService categoryService;

    public BannerController(BannerService bannerService, CreateBannerValidator createBannerValidator,
                            UpdateBannerValidator updateBannerValidator, CategoryService categoryService, BannerMapper bannerMapper, CategoryService categoryService1) {
        this.bannerService = bannerService;
        this.createBannerValidator = createBannerValidator;
        this.updateBannerValidator = updateBannerValidator;
        this.bannerMapper = bannerMapper;
        this.categoryService = categoryService1;
    }

    @GetMapping
    public List<BannerShortDTO> getBanners(@RequestParam(value = "query", required = false) String query) throws BannerNotFoundException {

        if (query != null)
            return bannerMapper.toBannerShortDTOList(bannerService.search(query));

        return bannerMapper.toBannerShortDTOList(bannerService.findAll());
    }

    @GetMapping("/{id}")
    public BannerDTO getBannerById(@PathVariable ("id") int id) throws BannerNotFoundException, CategoryNotFoundException {

        BannerDTO bannerDTO = bannerMapper.toBannerDTO(bannerService.findById(id));
        bannerDTO.setCategoryId(categoryService.findByBannerId(id).getId());
        return bannerDTO;

    }


    @PostMapping()
    public ResponseEntity<Integer> create(@RequestBody @Valid BannerCreateDTO bannerCreateDTO, BindingResult bindingResult) throws CategoryNotFoundException, BannerNotCreatedException {

        Category category = categoryService.findByName(bannerCreateDTO.getCategoryName());
        if (category == null)
            throw new CategoryNotFoundException("Category with name " + bannerCreateDTO.getCategoryName() + " was not found");
        Banner banner = bannerMapper.toEntity(bannerCreateDTO);
        createBannerValidator.validate(banner, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BannerNotCreatedException(createErrorMsg(bindingResult));// TODO придумать exception, сформировать msg
        }

        banner.setCategory(category);
        bannerService.save(banner);
        return new ResponseEntity<>(banner.getId(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid BannerCreateDTO updatedBannerDTO,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) throws CategoryNotFoundException, BannerNotFoundException, BannerNotUpdatedException {


        Banner updatedBanner = bannerMapper.toEntity(updatedBannerDTO);
        int categoryId = categoryService.findByName(updatedBannerDTO.getCategoryName()).getId();
        updatedBanner.setId(id);

        updateBannerValidator.validate(updatedBanner, bindingResult);

        if (bindingResult.hasErrors()){
            throw new BannerNotUpdatedException(createErrorMsg(bindingResult));
        }

        updatedBanner.setCategory(categoryService.findByName(updatedBanner.getName()));
        bannerService.update(id, updatedBanner, categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) throws BannerNotFoundException {

        bannerService.delete(id);
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
