DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS transactions;

CREATE TABLE users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  email VARCHAR(250) DEFAULT NULL,
  balance DECIMAL(20,2) DEFAULT 0.00
);

CREATE TABLE transactions (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  starting_balance DECIMAL(20,2),
  closing_balance DECIMAL(20,2),
  transaction_date DATE,
  user_id VARCHAR(250) NOT NULL
);


INSERT INTO users (id, first_name, last_name, email) VALUES
  (1000, 'Saurabh', 'Kumar', 'Saurabh.Kumar@gmail.com'),
  (2000, 'Bill', 'Gates', 'Bill.Gates@gmail.com');

INSERT INTO transactions (id, starting_balance, closing_balance, transaction_date, user_id) VALUES
  (1111, 0, 10, '2021-02-05', 1000),
  (1112, 10, 15, '2021-02-06', 1000),
  (1113, 15, 20, '2021-02-07', 1000),
  (1114, 20, 25, '2021-02-08', 1000);