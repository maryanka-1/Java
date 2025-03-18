INSERT INTO users (login, password) VALUES ('Maria', '123');
INSERT INTO users (login, password) VALUES ('Peter', '321');
INSERT INTO users (login, password) VALUES ('Marina', '654');
INSERT INTO users (login, password) VALUES ('Oleg', '456');
INSERT INTO users (login, password) VALUES ('Ivan', '789');

INSERT INTO chatroom (name, owner) VALUES ('Room_Maria', (SELECT id FROM users WHERE login = 'Maria'));
INSERT INTO chatroom (name, owner) VALUES ('Room_Peter', (SELECT id FROM users WHERE login = 'Peter'));
INSERT INTO chatroom (name, owner) VALUES ('Room_Marina', (SELECT id FROM users WHERE login = 'Marina'));
INSERT INTO chatroom (name, owner) VALUES ('Room_Oleg', (SELECT id FROM users WHERE login = 'Oleg'));
INSERT INTO chatroom (name, owner) VALUES ('Room_Ivan', (SELECT id FROM users WHERE login = 'Ivan'));

INSERT INTO messages (author, room, text, datetime) VALUES (
                                                             (SELECT id FROM users WHERE login = 'Maria'),
                                                             (SELECT id FROM chatroom WHERE name = 'Room_Maria'),
                                                             'Hello, I`m Maria!', '2025-02-12 12:00:00');
INSERT INTO messages (author, room, text, datetime) VALUES (
                                                                (SELECT id FROM users WHERE login = 'Peter'),
                                                                (SELECT id FROM chatroom WHERE name = 'Room_Maria'),
                                                                'Hello, Maria! I`m Peter!', '2025-02-12 12:02:00');
INSERT INTO messages (author, room, text, datetime) VALUES (
                                                                (SELECT id FROM users WHERE login = 'Marina'),
                                                                (SELECT id FROM chatroom WHERE name = 'Room_Oleg'),
                                                                'Hello, How are you?', '2025-02-12 12:25:00');
INSERT INTO messages (author, room, text, datetime) VALUES (
                                                                (SELECT id FROM users WHERE login = 'Oleg'),
                                                                (SELECT id FROM chatroom WHERE name = 'Room_Oleg'),
                                                                'I`m fine! How are you?', '2025-02-12 12:27:00');
INSERT INTO messages (author, room, text, datetime) VALUES (
                                                                (SELECT id FROM users WHERE login = 'Ivan'),
                                                                (SELECT id FROM chatroom WHERE name = 'Room_Peter'),
                                                                'Do you want to walk today?', '2025-02-12 12:56:00');
INSERT INTO messages (author, room, text, datetime) VALUES (
                                                                (SELECT id FROM users WHERE login = 'Peter'),
                                                                (SELECT id FROM chatroom WHERE name = 'Room_Peter'),
                                                                'Do you want to walk today?', '2025-02-12 12:56:00');