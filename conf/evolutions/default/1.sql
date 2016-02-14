CREATE SEQUENCE user_id_seq;
CREATE TABLE account (
    id         integer NOT NULL PRIMARY KEY DEFAULT nextval('user_id_seq'),
    password   varchar NOT NULL UNIQUE ,
    name       varchar NOT NULL,
    role       varchar NOT NULL
);
