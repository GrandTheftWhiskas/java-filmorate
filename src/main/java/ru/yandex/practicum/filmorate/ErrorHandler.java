package ru.yandex.practicum.filmorate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

@ControllerAdvice("ru.yandex.practicum.filmorate")
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValid(ValidationException e) {
        return "Произошла ошибка валидации: " + e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NullPointerException e) {
        return "Аргумент не найден: " + e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleServerError(HttpServerErrorException.InternalServerError e) {
        return "Произошла ошибка на стороне сервера: " + e.getMessage();
    }
}
