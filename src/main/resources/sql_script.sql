DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS companies;

CREATE TABLE companies (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(32) UNIQUE,
                       firstname VARCHAR(32),
                       lastname VARCHAR(32),
                       birth_day DATE,
                       company_id INT REFERENCES companies (id),
                       role VARCHAR(32)
);