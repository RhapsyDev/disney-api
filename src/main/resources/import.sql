INSERT INTO GENRES (id, name) VALUES (1, 'Accion');
INSERT INTO GENRES (id, name) VALUES (2, 'Comedia');
INSERT INTO GENRES (id, name) VALUES (3, 'Aventura');
INSERT INTO GENRES (id, name) VALUES (4, 'Romance');

INSERT INTO CHARACTERS (id, name , image, age, weight, history) VALUES (1, 'Mickey Mouse', 'image-url1', 20, 30.3, 'History 1');
INSERT INTO CHARACTERS (id, name , image, age, weight, history) VALUES (2, 'Pluto', 'image-url2', 30, 20.5, 'History 2');
INSERT INTO CHARACTERS (id, name , image, age, weight, history) VALUES (3, 'Aladdin', 'image-url3', 10, 10.6, 'History 3');
INSERT INTO CHARACTERS (id, name , image, age, weight, history) VALUES (4, 'Bestia', 'image-url4', 35, 50.5, 'History 4');
INSERT INTO CHARACTERS (id, name , image, age, weight, history) VALUES (5, 'Donald', 'image-url5', 20, 14.0, 'History 5');
INSERT INTO CHARACTERS (id, name , image, age, weight, history) VALUES (6, 'Mulan', 'image-url6', 5, 123.2, 'History 6');
INSERT INTO CHARACTERS (id, name , image, age, weight, history) VALUES (7, 'Minnie Mouse', 'image-url7', 20, 30.3, 'History 7');

INSERT INTO MOVIES (id, title, image, released_on, rating, genre_id) VALUES (1, 'Coco', 'movie-url1', DATE '1991-09-23', 5, 2 );
INSERT INTO MOVIES (id, title, image, released_on, rating, genre_id) VALUES (2, 'El Rey Leon', 'movie-url1', DATE '2022-10-17', 3, 1 );
INSERT INTO MOVIES (id, title, image, released_on, rating, genre_id) VALUES (3, 'Encanto', 'movie-url1', DATE '2005-03-07', 2, 4 );
INSERT INTO MOVIES (id, title, image, released_on, rating, genre_id) VALUES (4, '101 Dalmatas', 'movie-url1', DATE '1999-09-15', 5, 4 );

INSERT INTO MOVIE_CHARACTER (movie_id, character_id) VALUES ( 4, 1 );
INSERT INTO MOVIE_CHARACTER (movie_id, character_id) VALUES ( 1, 1 );
INSERT INTO MOVIE_CHARACTER (movie_id, character_id) VALUES ( 2, 6 );
INSERT INTO MOVIE_CHARACTER (movie_id, character_id) VALUES ( 3, 4 );
INSERT INTO MOVIE_CHARACTER (movie_id, character_id) VALUES ( 3, 2 );