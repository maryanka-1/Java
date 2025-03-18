DROP TABLE IF EXISTS users, chatroom, messages;

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS chatroom (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    owner INT NOT NULL,
    FOREIGN KEY (owner) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS messages (
    id BIGSERIAL PRIMARY KEY,
    author INT NOT NULL,
    room INT NOT NULL,
    text VARCHAR(255) NOT NULL,
    datetime TIMESTAMP,
    FOREIGN KEY (author) REFERENCES users (id),
    FOREIGN KEY (room) REFERENCES chatroom (id)
);
