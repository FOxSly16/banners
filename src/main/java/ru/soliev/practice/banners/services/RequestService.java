package ru.soliev.practice.banners.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.soliev.practice.banners.models.Banner;
import ru.soliev.practice.banners.models.Request;
import ru.soliev.practice.banners.repositories.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestService{

    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request createRequest(HttpServletRequest httpServletRequest) {

        LocalDateTime dateTime = LocalDateTime.now();
        String userAgent = httpServletRequest.getHeader("User-Agent");
        String ip = httpServletRequest.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank()) {
            ip = httpServletRequest.getRemoteAddr();
        }

        Request request = new Request();
        request.setTime(dateTime);
        request.setUserAgent(userAgent);
        request.setIp(ip);

        return request;
    }

    @Transactional
    public void save(Request request) {
        requestRepository.save(request);
    }

    @Transactional(readOnly = true)
    public boolean hasBannerShowed(int bannerId, Request request) {
        String ip = request.getIp();
        String userAgent = request.getUserAgent();
        LocalDateTime time = request.getTime();

        List<Request> requests = requestRepository.findByIpAndUserAgentAndBanner_IdAndTimeGreaterThan(ip, userAgent,
                bannerId, time.minusMinutes(1));

        return !requests.isEmpty();
    }

    public Banner showBanner(List<Banner> banners, Request request) {
        for (Banner banner : banners) {
            if (!hasBannerShowed(banner.getId(), request)) {
                return banner;
            }
        }

        return null;
    }
}
