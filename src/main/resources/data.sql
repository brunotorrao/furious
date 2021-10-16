CREATE TABLE movie
(
  id          bigint PRIMARY KEY AUTO_INCREMENT,
  title       VARCHAR(250) NOT NULL,
  external_id VARCHAR(250) NOT NULL
);

INSERT INTO movie (external_id, title)
VALUES ('tt0232500', 'The Fast and the Furious');
INSERT INTO movie (external_id, title)
VALUES ('tt0322259', '2 Fast 2 Furious');
INSERT INTO movie (external_id, title)
VALUES ('tt0463985', 'The Fast and the Furious: Tokyo Drift');
INSERT INTO movie (external_id, title)
VALUES ('tt1013752', 'Fast & Furious');
INSERT INTO movie (external_id, title)
VALUES ('tt1596343', 'Fast Five');
INSERT INTO movie (external_id, title)
VALUES ('tt1905041', 'Fast & Furious 6');
INSERT INTO movie (external_id, title)
VALUES ('tt2820852', 'Furious 7');
INSERT INTO movie (external_id, title)
VALUES ('tt4630562', 'The Fate of the Furious');

CREATE TABLE movie_time
(
  id  bigint PRIMARY KEY AUTO_INCREMENT,
  date timestamp not null,
  price decimal not null,
  movie_id bigint not null,
  foreign key (movie_id) references movie(id),
  constraint date_movie_id_ux unique (movie_id, date)
);

CREATE INDEX movie_time_movie_id_x ON movie_time(movie_id);
CREATE INDEX movie_time_movie_id_date_x ON movie_time(movie_id, date);

INSERT INTO movie_time (date, price, movie_id)
VALUES (parsedatetime('15/10/2021 20:30', 'dd/MM/yyyy HH:mm'), 15.6, 1);

CREATE TABLE review
(
  id bigint PRIMARY KEY AUTO_INCREMENT,
  movie_id bigint not null,
  customer_id bigint not null,
  rating int not null,

  foreign key (movie_id) references movie(id),
  constraint customer_id_movie_id_ux unique (customer_id, movie_id)

);

CREATE INDEX review_customer_id_x ON review(customer_id);
CREATE INDEX review_movie_id_x ON review(movie_id);
