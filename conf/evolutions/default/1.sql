# Tasks schema

# --- !Ups
CREATE TABLE account (
    id         integer NOT NULL PRIMARY KEY,
    password   varchar NOT NULL,
    name       varchar NOT NULL UNIQUE ,
    role       varchar NOT NULL
);

# --- !Downs

DROP TABLE task;