package ru.soliev.practice.banners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByIdAndDeletedFalse(int id);

    Optional<Category> findByName(String name);

    Optional<Category> findByReqName(String name);

    @Query("SELECT c FROM Category c WHERE c.deleted = false AND LOWER(c.name) LIKE CONCAT(LOWER(:query), '%')")
    List<Category> findByQuery(String query);

    List<Category> findByDeletedFalse();

    Optional<Category> findByNameAndIdNot(String name, int id);

    Optional<Category> findByReqNameAndIdNot(String name, int id);

    Optional<Category> findByBanners_Id(int id);

}
