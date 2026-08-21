package ru.soliev.practice.banners.exceptions;

import java.time.LocalDateTime;

public class BannerErrorResponse {

    private String msg;
    private LocalDateTime time;

    public BannerErrorResponse(LocalDateTime time, String msg) {
        this.time = time;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
