package ru.soliev.practice.banners.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.models.Category;


import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

    Optional<Banner> findByIdAndDeletedFalse(int id);

    List<Banner> findByDeletedFalse();

    Optional<Banner> findByNameAndIdNot(String name, int id);

    Optional<Banner> findByName(String name);


    @Query("SELECT b FROM Banner b WHERE b.deleted = false AND LOWER(b.name) LIKE CONCAT(LOWER(:query), '%')")
    List<Banner> findByQuery(String query);

    List<Banner> findByCategory_IdAndDeletedFalseOrderByPriceDesc(int id);
}
