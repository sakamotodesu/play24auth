# Tasks schema

# --- !Ups

CREATE TABLE account (
    id         integer NOT NULL PRIMARY KEY,
    password   varchar NOT NULL UNIQUE,
    name       varchar NOT NULL,
    role       varchar NOT NULL
);

# --- !Downs

DROP TABLE task;