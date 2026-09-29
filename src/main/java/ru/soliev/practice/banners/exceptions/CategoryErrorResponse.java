package ru.soliev.practice.banners.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class CategoryErrorResponse {
    private String msg;
    private LocalDateTime time;

}
