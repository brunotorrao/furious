CREATE TABLE movie_time
(
  id  SERIAL PRIMARY KEY,
  date timestamp not null,
  price decimal not null,
  movie_id bigint not null,
  foreign key (movie_id) references movie(id),
  constraint date_movie_id_ux unique (movie_id, date)
);

CREATE INDEX movie_time_movie_id_x ON movie_time(movie_id);
CREATE INDEX movie_time_movie_id_date_x ON movie_time(movie_id, date);

INSERT INTO public.movie_time (date, price, movie_id) VALUES ('2021-10-15 20:30:00.000000', 15.6, 1);

