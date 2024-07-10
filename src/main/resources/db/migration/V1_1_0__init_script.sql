CREATE TABLE IF NOT EXISTS users (
    id bigserial primary key,
    username varchar(255) unique
);