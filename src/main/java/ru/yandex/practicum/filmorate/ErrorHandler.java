package ru.yandex.practicum.filmorate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

@RestControllerAdvice("ru.yandex.practicum.filmorate")
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static HttpStatus handleValid(ValidationException e) {
        System.out.println("Ошибка 400: " + e.getMessage());
        return HttpStatus.BAD_REQUEST;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static HttpStatus handleNotFound(NotFoundException e) {
        System.out.println("Ошибка 404: " + e.getMessage());
        return HttpStatus.NOT_FOUND;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static HttpStatus handleServerError(HttpServerErrorException.InternalServerError e) {
        System.out.println("Ошибка 500: " + e.getMessage());
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
