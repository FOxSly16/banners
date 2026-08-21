package ru.soliev.practice.banners.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BannerNotFoundException.class)
    public ResponseEntity<BannerErrorResponse> handleBannerNotFoundException(BannerNotFoundException e) {
        BannerErrorResponse response = new BannerErrorResponse(
                LocalDateTime.now(),
                e.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BannerNotCreatedException.class)
    public ResponseEntity<BannerErrorResponse> handleBannerNotCreatedException(BannerNotCreatedException e) {
        BannerErrorResponse response = new BannerErrorResponse(
                LocalDateTime.now(),
                e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BannerNotUpdatedException.class)
    public ResponseEntity<BannerErrorResponse> handleBannerNotUpdatedException(BannerNotUpdatedException e) {
        BannerErrorResponse response = new BannerErrorResponse(
                LocalDateTime.now(),
                e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<CategoryErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException e) {
        CategoryErrorResponse response = new CategoryErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotCreatedException.class)
    public ResponseEntity<CategoryErrorResponse> handleCategoryNotCreatedException(CategoryNotCreatedException e) {
        CategoryErrorResponse response = new CategoryErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotUpdatedException.class)
    public ResponseEntity<CategoryErrorResponse> handleCategoryNotUpdatedException(CategoryNotUpdatedException e) {
        CategoryErrorResponse response = new CategoryErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotDeletedException.class)
    public ResponseEntity<CategoryErrorResponse> handleCategoryNotDeletedException(CategoryNotDeletedException e) {
        CategoryErrorResponse response = new CategoryErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
