package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
class UserApplicationTests {
    User user;
    private final UserDbStorage userDbStorage;

    @BeforeEach
    void setUp() {
        user = new User("mail@ru", "1234", LocalDate.of(1999, 12, 18));
        user.setName("name");
        userDbStorage.postUser(user);
    }

    @Test
    public void couldFindUserById() {
        Optional<User> user = Optional.ofNullable(userDbStorage.getUser(1));

        assertThat(user)
                .isPresent()
                .hasValueSatisfying(user1 ->
                        assertThat(user1).hasFieldOrPropertyWithValue("id", 1L)
                );
    }

    @Test
    public void couldUpdateUser() {
        user.setName("новое имя");
        userDbStorage.putUser(user);
        User user1 = userDbStorage.putUser(user);

        assertThat(user1).hasFieldOrPropertyWithValue("name", "новое имя");
    }

    @Test
    public void couldGetUsersList() {
        User user1 = new User("mail1@ru", "12345", LocalDate.of(1999, 12, 16));
        user1.setName("другое имя");
        userDbStorage.postUser(user1);
        List<User> users = userDbStorage.getUsers();
        assertThat(users).hasSize(2);
    }

    @Test
    public void couldGetUser() {
        Optional<User> newUser = Optional.ofNullable(userDbStorage.getUser(user.getId()));

        assertThat(newUser).isPresent();
        assertEquals(user, newUser.get());
    }

    @Test
    public void couldSetFriend() {
        User user1 = new User("mail1@ru", "12345", LocalDate.of(1999, 12, 16));
        user1.setName("имя");

        user1 = userDbStorage.postUser(user1);

        userDbStorage.addFriend(user1.getId(), user.getId());

        assertEquals(user.getId(), userDbStorage.getFriends(user1.getId()).getFirst().getId());
    }

    @Test
    public void couldGetCommonFriends() {
        User user1 = new User("mail1@ru", "12345", LocalDate.of(1999, 12, 16));
        user1.setName("имя");

        User user2 = new User("mail2@ru", "135", LocalDate.of(2002, 10, 19));
        user1.setName("имя");

        user1 = userDbStorage.postUser(user1);
        user2 = userDbStorage.postUser(user2);

        userDbStorage.addFriend(user1.getId(), user.getId());
        userDbStorage.addFriend(user2.getId(), user.getId());

        assertEquals(user.getId(), userDbStorage.getMutualFriends(user1.getId(), user2.getId()).getFirst().getId());
    }
}


