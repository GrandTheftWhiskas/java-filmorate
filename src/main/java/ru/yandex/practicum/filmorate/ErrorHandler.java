package ru.yandex.practicum.filmorate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

@RestControllerAdvice("ru.yandex.practicum.filmorate")
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static StackTraceElement[] handleValid(ValidationException e) {
        return e.getStackTrace();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static StackTraceElement[] handleNotFound(NullPointerException e) {
        return e.getStackTrace();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static StackTraceElement[] handleServerError(HttpServerErrorException.InternalServerError e) {
        return e.getStackTrace();
    }
}
