CREATE TABLE movie
(
  id          SERIAL PRIMARY KEY,
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
