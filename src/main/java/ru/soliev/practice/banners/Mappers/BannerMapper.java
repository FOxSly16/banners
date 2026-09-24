package ru.soliev.practice.banners.Mappers;

import org.mapstruct.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ru.soliev.practice.banners.dto.CreateBannerDTO;
import ru.soliev.practice.banners.dto.BannerShortDTO;
import ru.soliev.practice.banners.dto.BannerDTO;
import ru.soliev.practice.banners.dto.UpdateBannerDTO;
import ru.soliev.practice.banners.models.Banner;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR) // прописываем "spring", чтобы класс, который будет создан библой
// для реализации методов, был помечен @Component,
// благодаря второму параметру, в случае если какое-то поле не промапиться, выброситься ошибка компиляции
public interface BannerMapper {

    @Mapping(target = "id", ignore = true) // игнорируем то, что эти поля непромаппятся
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "requests", ignore = true)
    Banner toEntity(BannerDTO bannerDTO);

    @Mapping(target = "categoryId", source = "category.id")
    BannerDTO toBannerDTO(Banner banner);

    List<Banner> toBannerList(List<BannerDTO> bannerDTOList);

    List<BannerDTO> toBannerDTOList(List<Banner> bannerList);

    BannerShortDTO toBannerShortDTO(Banner banner);

    List<BannerShortDTO> toBannerShortDTOList(List<Banner> bannerList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "requests", ignore = true)
    Banner toEntity(CreateBannerDTO createBannerDTO);

    @Mapping(target = "categoryName", source = "category.name")
    CreateBannerDTO toCreateBannerDTO(Banner banner);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "requests", ignore = true)
    Banner toEntity(UpdateBannerDTO updatedBannerDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "requests", ignore = true)
    void updateBannerFromDTO(UpdateBannerDTO updateBannerDTO,
                             @MappingTarget Banner banner);
}
