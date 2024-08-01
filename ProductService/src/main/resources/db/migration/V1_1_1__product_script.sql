ALTER TABLE users
  ADD COLUMN products bigserial unique;


CREATE TABLE IF NOT EXISTS products (
    id bigserial primary key,
    productsid bigint references users(products),
    accountNumber varchar(30),
    balance numeric,
    productType integer
);

