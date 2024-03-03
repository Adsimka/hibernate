DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS companies;
DROP TABLE IF EXISTS profile;

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

CREATE TABLE profile (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL UNIQUE REFERENCES users (id),
                         street VARCHAR(128),
                         language CHAR(2)
);

CREATE TABLE chat (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(32) NOT NULL UNIQUE
);

DROP TABLE users_chat;

CREATE TABLE users_chat (
                            id BIGSERIAL PRIMARY KEY,
                            user_id BIGINT REFERENCES users (id),
                            chat_id BIGINT REFERENCES chat (id),
                            created_by VARCHAR(32) NOT NULL,
                            created_time TIMESTAMP NOT NULL
);

CREATE TABLE company_locale (
                                company_id INT NOT NULL REFERENCES companies (id),
                                lang CHAR(2),
                                description VARCHAR(32),
                                PRIMARY KEY(company_id, lang)
);

DELETE FROM users WHERE id = 12;

DROP TABLE company_locale;
DROP TABLE users;
DROP TABLE companies;
DROP TABLE chat;

SELECT * FROM users;
SELECT * FROM companies;

SELECT
    username
FROM users
WHERE company_id = 1;