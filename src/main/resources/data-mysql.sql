INSERT INTO users VALUES ('john', '{bcrypt}$2y$10$//E5FfxnzarqqvjsN/IUTubgZTMQxh8d5T1BkU1jh9KmgkqxEZCrG', 1);
INSERT INTO users VALUES ('mary', '{bcrypt}$2y$10$0jyxprwNX8NJ2ivEziMb1ebTcIhKIbUalpADJMX92B3t7bcihAtIe', 1);
INSERT INTO users VALUES ('guest', '{bcrypt}$2y$10$TfgqRMz0xswwwLJ8RWcVAOaUjQAPeHuMnmSjFZuK0O5bc4//8F6RK', 1);

INSERT INTO authorities VALUES ('john', 'ROLE_USER');
INSERT INTO authorities VALUES ('mary', 'ROLE_USER');
INSERT INTO authorities VALUES ('mary', 'ROLE_ADMIN');
INSERT INTO authorities VALUES ('guest', 'ROLE_GUEST');
