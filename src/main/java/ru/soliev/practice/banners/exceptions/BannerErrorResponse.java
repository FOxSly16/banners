package ru.soliev.practice.banners.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BannerErrorResponse {
    private String msg;
    private LocalDateTime time;
}
