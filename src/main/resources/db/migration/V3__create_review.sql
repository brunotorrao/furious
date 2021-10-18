CREATE TABLE review
(
  id SERIAL PRIMARY KEY,
  movie_id bigint not null,
  customer_id bigint not null,
  rating int not null,

  foreign key (movie_id) references movie(id),
  constraint customer_id_movie_id_ux unique (customer_id, movie_id)

);
