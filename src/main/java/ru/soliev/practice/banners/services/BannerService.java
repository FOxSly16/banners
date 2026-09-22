package ru.soliev.practice.banners.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.soliev.practice.banners.dto.UpdateBannerDTO;
import ru.soliev.practice.banners.exceptions.BannerNotFoundException;
import ru.soliev.practice.banners.exceptions.BannerNotUpdatedException;
import ru.soliev.practice.banners.exceptions.CategoryNotFoundException;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.repositories.BannerRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final CategoryService categoryService;

    @Autowired
    public BannerService(BannerRepository bannerRepository, CategoryService categoryService) {
        this.bannerRepository = bannerRepository;
        this.categoryService = categoryService;
    }

    @Transactional(readOnly = true)
    public List<Banner> findAll() {
        return bannerRepository.findByDeletedFalse();
    }

    @Transactional(readOnly = true)
    public Banner findByNameExceptThisId(String name, int id) {
        return bannerRepository.findByNameAndIdNot(name, id).orElse(null);
    }


    @Transactional(readOnly = true)
    public Banner findById(int id) throws BannerNotFoundException {
        return bannerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BannerNotFoundException("Banner with id " + id + " was not found"));
    }

    @Transactional(readOnly = true)
    public Banner findByName(String name) {
        return bannerRepository.findByName(name).orElse(null);
    }

    @Transactional
    public void update(int id, UpdateBannerDTO updateBannerDTO) throws CategoryNotFoundException, BannerNotFoundException, BannerNotUpdatedException {

        int modCount = 0;
        Banner banner = bannerRepository.findByIdAndDeletedFalse(id).
                orElseThrow(() -> new BannerNotFoundException("Banner with id " + id + " was not found"));

        if (updateBannerDTO.getCategoryName() != null) {
            banner.setCategory(categoryService.findByNameAndDeletedFalse(updateBannerDTO.getCategoryName()));
            modCount += 1;
        }

        if (updateBannerDTO.getContent() != null) {
            banner.setContent(updateBannerDTO.getContent());
            modCount += 1;
        }

        if (updateBannerDTO.getName() != null) {
            banner.setName(updateBannerDTO.getName());
            modCount += 1;
        }

        if (updateBannerDTO.getPrice() != null) {
            banner.setPrice(updateBannerDTO.getPrice());
            modCount += 1;
        }

        if (modCount == 0)
            throw new BannerNotUpdatedException("Banner was not updated");
        saveBanner(banner);
    }

    @Transactional
    public void delete(int id) throws BannerNotFoundException {
        Banner banner = bannerRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new BannerNotFoundException("Banner with id " + id + " was not found"));
        banner.setDeleted(true);
    }

    @Transactional(readOnly = true)
    public List<Banner> search(String query) throws BannerNotFoundException {
        return bannerRepository.findByQuery(query);
    }

    @Transactional
    public void saveBanner(Banner banner) throws CategoryNotFoundException {
        banner.getCategory().getBanners().add(banner);

        bannerRepository.save(banner);
    }

    @Transactional(readOnly = true)
    public List<Banner> findByCategoryIdOrderedDesc(int id) {
        return bannerRepository.findByCategory_IdAndDeletedFalseOrderByPriceDesc(id);
    }

    @Transactional(readOnly = true)
    public List<Banner> findByCategoryIdWithRequests(int categoryId) {
        return bannerRepository.findByCategoryIdWithRequests(categoryId, LocalDateTime.now().minusMinutes(2));
    }
}
