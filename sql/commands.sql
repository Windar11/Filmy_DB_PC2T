CREATE DATABASE Film_db IF NOT EXISTS;

DROP DATABASE Film_db IF EXISTS;

CREATE TABLE Film_db.Film (
    id INT NOT NULL AUTO_INCREMENT,
    name TEXT,
    release_year SMALLINT,
    recommended_age INT,
    PRIMARY KEY (id)
);

CREATE TABLE Film_db.Crew (
    id INT NOT NULL AUTO_INCREMENT,
    film_id INT NOT NULL,
    name TEXT,
    occupation TINYINT,
    PRIMARY KEY (id)
);

CREATE TABLE Film_db.Review (
    id INT NOT NULL AUTO_INCREMENT,
    film_id INT NOT NULL,
    score TINYINT,
    comment TEXT,
    PRIMARY KEY (id)
);

TRUNCATE TABLE Film_db.Database.Film;
DROP TABLE Film_db.Film;

TRUNCATE TABLE Film_db.Crew;
DROP TABLE Film_db.Crew;

TRUNCATE TABLE Film_db.Database.Review;
DROP TABLE Film_db.Review;
