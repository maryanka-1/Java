DROP TABLE IF EXISTS product

CREATE TABLE IF NOT EXISTS product (
id INTEGER PRIMARY KEY NOT NULL,
name VARCHAR(250) NOT NULL,
price FLOAT NOT NULL
);