DROP DATABASE IF EXISTS LESSON08;

CREATE DATABASE LESSON08;

USE LESSON08;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    age INT,
    city VARCHAR(100)
);

-- Step 2:Populate Data
SET SESSION cte_max_recursion_depth = 1000000;

INSERT INTO users (name, email, age, city)
WITH RECURSIVE numbers AS (
    SELECT 1 AS id
    UNION ALL
    SELECT id + 1 FROM numbers WHERE id < 1000000
)
SELECT 
    CONCAT('User', id),
    CONCAT('user', id, '@example.com'),
    FLOOR(18 + (RAND() * 60)),  -- Age between 18 and 78
    IF(RAND() < 0.5, 'New York', 'Los Angeles')
FROM numbers;


select count(*) from users;


-- Query without an index
EXPLAIN ANALYZE 
SELECT * FROM users WHERE email = 'user500000@example.com';


-- CREATE INDEX idx_users_email ON users(email);

-- EXPLAIN ANALYZE 
-- SELECT * FROM users WHERE email = 'user500000@example.com';




