# База данных
![____](https://github.com/GrandTheftWhiskas/java-filmorate/tree/add-database/src/resources/database.png)

## Таблицы и их поля:

### users

- id: Уникальный идентификатор пользователя (первичный и внешний ключ)
- name: Имя пользователя
- login: Логн пользователя
- email: Адрес электронной почты пользователя
- birthday: Дата рождения пользователя

### friends

- id: Идентификатор друзей пользователя
- status: Статус дружбы (CONFIRMED/UNCONFIRMED)

### films

- id: Идентификатор фильма (первичный и внешний ключ)
- name: Название фильма
- description: Eго описание
- genre: Жанр фильма
- rating: Рейтинг фильма
- release_date: Дата релиза фильма
- duration: Продолжительность в минутах
- likes: Количество лайков

### likes

- id: Идентификатор пользователей, поставивших лайк



# Примеры запросов


#### Получение 10-ти фильмов по популярности
    
	SELECT name, likes
	FROM films
	ORDER BY likes DESC
	LIMIT 10;


#### Получение списка подтвержденных друзей

    SELECT name, email
	FROM users
	WHERE id IN (
	SELECT id
	FROM friends
	WHERE status = 'CONFIRMED'
	);

#### Получение любимых фильмов пользователя
    
    SELECT name
	FROM films
    WHERE id IN (
	SELECT id
	FROM likes
	WHERE id = 1
	);
    

