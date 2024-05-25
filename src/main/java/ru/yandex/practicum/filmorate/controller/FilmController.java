package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.yandex.practicum.filmorate.service.FilmService;

@Controller
public class FilmController {
   private FilmService filmService;

   @Autowired
   public FilmController(FilmService filmService) {
       this.filmService = filmService;
   }
}
