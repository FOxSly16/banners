package ru.soliev.practice.banners.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.soliev.practice.banners.Mappers.BannerMapper;;
import ru.soliev.practice.banners.dto.CreateBannerDTO;
import ru.soliev.practice.banners.dto.BannerShortDTO;
import ru.soliev.practice.banners.dto.BannerDTO;
import ru.soliev.practice.banners.dto.UpdateBannerDTO;
import ru.soliev.practice.banners.exceptions.BannerNotCreatedException;
import ru.soliev.practice.banners.exceptions.BannerNotFoundException;
import ru.soliev.practice.banners.exceptions.BannerNotUpdatedException;
import ru.soliev.practice.banners.exceptions.CategoryNotFoundException;
import ru.soliev.practice.banners.managers.BannerManager;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.services.BannerService;
import ru.soliev.practice.banners.services.CategoryService;
import ru.soliev.practice.banners.util.CreateBannerValidator;
import ru.soliev.practice.banners.util.UpdateBannerValidator;

import java.util.List;

@RestController
@RequestMapping("/banners")
public class BannerController {

    private final BannerService bannerService;
    private final CreateBannerValidator createBannerValidator;
    private final UpdateBannerValidator updateBannerValidator;
    private final BannerMapper bannerMapper;
    private final BannerManager bannerManager;

    public BannerController(BannerService bannerService, CreateBannerValidator createBannerValidator,
                            UpdateBannerValidator updateBannerValidator, CategoryService categoryService, BannerMapper bannerMapper, CategoryService categoryService1, BannerManager bannerManager) {
        this.bannerService = bannerService;
        this.createBannerValidator = createBannerValidator;
        this.updateBannerValidator = updateBannerValidator;
        this.bannerMapper = bannerMapper;
        this.bannerManager = bannerManager;
    }

    @GetMapping
    public List<BannerShortDTO> getBanners(@RequestParam(value = "query", required = false) String query) throws BannerNotFoundException {

        if (query != null)
            return bannerMapper.toBannerShortDTOList(bannerService.search(query));

        return bannerMapper.toBannerShortDTOList(bannerService.findAll());
    }

    @GetMapping("/{id}")
    public BannerDTO getBannerById(@PathVariable ("id") int id) throws BannerNotFoundException, CategoryNotFoundException {

        return bannerMapper.toBannerDTO(bannerService.findById(id));

    }


    @PostMapping()
    public ResponseEntity<Integer> create(@RequestBody @Valid CreateBannerDTO createBannerDTO, BindingResult bindingResult) throws CategoryNotFoundException, BannerNotCreatedException {

        createBannerValidator.validate(createBannerDTO.getName(), bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BannerNotCreatedException(createErrorMsg(bindingResult));//
        }

        Banner banner = bannerManager.createBanner(createBannerDTO);
        return new ResponseEntity<>(banner.getId(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody @Valid UpdateBannerDTO updateBannerDTO,
                       BindingResult bindingResult,
                       @PathVariable("id") int id) throws CategoryNotFoundException, BannerNotFoundException, BannerNotUpdatedException {

        if (updateBannerDTO.getName() != null) {

            updateBannerValidator.validate(id, updateBannerDTO.getName(), bindingResult);
        }

        if (bindingResult.hasErrors()){
            throw new BannerNotUpdatedException(createErrorMsg(bindingResult));
        }

        bannerService.update(id, updateBannerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) throws BannerNotFoundException {
        bannerService.delete(id);
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
