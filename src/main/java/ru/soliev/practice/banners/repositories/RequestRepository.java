package ru.soliev.practice.banners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.soliev.practice.banners.models.Request;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findByIpAndUserAgentAndBanner_IdAndTimeGreaterThan(String ip, String userAgent, int id, LocalDateTime time);
}
