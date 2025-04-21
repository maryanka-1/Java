DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
id BIGSERIAL PRIMARY KEY,
email VARCHAR(70) NOT NULL,
password VARCHAR(70)
);

INSERT INTO users (email)
VALUES ('mary@mail.ru'),
        ('mmmmmm@mail.ru'),
        ('nnnnnn@mail.ru'),
        ('lllllll@mail.ru'),
        ('sssssss@mail.ru');
