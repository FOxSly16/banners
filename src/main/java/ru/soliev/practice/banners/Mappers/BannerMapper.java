package ru.soliev.practice.banners.Mappers;

import org.mapstruct.Mapper;

import ru.soliev.practice.banners.dto.BannerCreateDTO;
import ru.soliev.practice.banners.dto.BannerShortDTO;
import ru.soliev.practice.banners.dto.BannerDTO;
import ru.soliev.practice.banners.models.Banner;

import java.util.List;

@Mapper(componentModel = "spring") // прописываем "spring", чтобы класс, который будет создан библой для реализации методов,
// был помечен @Component
public interface BannerMapper {

    Banner toEntity(BannerDTO bannerDTO);

    BannerDTO toBannerDTO(Banner banner);

    List<Banner> toBannerList(List<BannerDTO> bannerDTOList);

    List<BannerDTO> toBannerDTOList(List<Banner> bannerList);

    BannerShortDTO toBannerShortDTO(Banner banner);

    List<BannerShortDTO> toBannerShortDTOList(List<Banner> bannerList);

    Banner toEntity(BannerCreateDTO bannerCreateDTO);

    BannerCreateDTO toBannerCreateDTO(Banner banner);
}
