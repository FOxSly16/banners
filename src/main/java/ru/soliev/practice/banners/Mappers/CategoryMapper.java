package ru.soliev.practice.banners.Mappers;

import org.mapstruct.Mapper;
import ru.soliev.practice.banners.dto.CategoryDTO;
import ru.soliev.practice.banners.models.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toCategoryDTO(Category category);

    List<Category> toEntityList(List<CategoryDTO> categoryDTOList);

    List<CategoryDTO> toCategoryDTOList(List<Category> categoryList);
}
