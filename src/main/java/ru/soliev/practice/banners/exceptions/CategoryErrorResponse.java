package ru.soliev.practice.banners.exceptions;

import java.time.LocalDateTime;

public class CategoryErrorResponse {
    LocalDateTime time;
    String msg;

    public CategoryErrorResponse(String msg, LocalDateTime time) {
        this.msg = msg;
        this.time = time;
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
