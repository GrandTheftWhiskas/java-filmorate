package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;

@SpringBootTest
class FilmorateApplicationTests {
    FilmController filmController = new FilmController();
	UserController userController = new UserController();

	@Test
	void filmAddTest() {
		Film film = new Film("Титаник", "Романтическая драма и фильм-катастрофа",
				LocalDate.of(1997, Month.DECEMBER, 16), Duration.ofMinutes(194));
		Assertions.assertNotNull(filmController.filmPost(film));
	}

	@Test
	void filmPutTest() {
	    Film film = new Film("Неудержимые", "Боевик",
				LocalDate.of(2010, Month.AUGUST, 12), Duration.ofMinutes(109));
		Assertions.assertNull(filmController.filmPut(film));
	}

	@Test
	void getFilmsTest() {
		Film film = new Film("Титаник", "Романтическая драма и фильм-катастрофа",
				LocalDate.of(1997, Month.DECEMBER, 16), Duration.ofMinutes(194));
		Film film1 = new Film("Неудержимые", "Боевик",
				LocalDate.of(2010, Month.AUGUST, 12), Duration.ofMinutes(109));
		filmController.filmPost(film);
		filmController.filmPost(film1);
		Assertions.assertTrue(filmController.filmGet().size() == 2);
	}

	@Test
	void userAddTest() {
		User user = new User();
		user.setEmail("example@gmail.com");
		user.setLogin("Vasya117");
		user.setName("Василий");
		user.setBirthday(LocalDate.of(2001, Month.NOVEMBER, 12));
		Assertions.assertNotNull(userController.userPost(user));
	}

	@Test
	void userPutTest() {
		User user = new User();
		user.setEmail("example@gmail.com");
		user.setLogin("Vasya117");
		user.setName("Василий");
		user.setBirthday(LocalDate.of(2001, Month.NOVEMBER, 12));
		Assertions.assertNull(userController.userPut(user));
	}

	@Test
	void usersGetTest() {
		User user = new User();
		user.setEmail("example@gmail.com");
		user.setLogin("Vasya117");
		user.setName("Василий");
		user.setBirthday(LocalDate.of(2001, Month.NOVEMBER, 12));

		User user1 = new User();
		user1.setEmail("example1@gmail.com");
		user1.setLogin("Vadim19");
		user1.setName(" ");
		user1.setBirthday(LocalDate.of(1990, Month.APRIL, 19));

		userController.userPost(user);
		userController.userPost(user1);
		Assertions.assertTrue(userController.usersGet().size() == 2);
	}
}
