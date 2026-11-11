
-- Drop tables if exists to "clean up" database for testing.
DROP TABLE IF EXISTS orderline_history CASCADE;
DROP TABLE IF EXISTS order_history CASCADE;
DROP TABLE IF EXISTS orderlines CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS cupcake_tops CASCADE;
DROP TABLE IF EXISTS cupcake_bottoms CASCADE;
DROP TABLE IF EXISTS admins CASCADE;
DROP TABLE IF EXISTS customers CASCADE;

-- 1. CUSTOMERS
CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    email VARCHAR UNIQUE,
    name VARCHAR,
    password VARCHAR,
    balance DOUBLE PRECISION
);

-- 2. ADMINS
CREATE TABLE admins (
    admin_id SERIAL PRIMARY KEY,
    email VARCHAR UNIQUE,
    name VARCHAR,
    password VARCHAR
);

-- 3. TOPPINGS
CREATE TABLE cupcake_tops (
    flavor VARCHAR PRIMARY KEY,
    price DOUBLE PRECISION
);

-- 4. BOTTOMS
CREATE TABLE cupcake_bottoms (
    flavor VARCHAR PRIMARY KEY,
    price DOUBLE PRECISION
);

-- 5. ORDERS
CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    customer_id INTEGER REFERENCES customers(customer_id),
    status VARCHAR,
	order_date DATE DEFAULT CURRENT_DATE
);

-- 6. ORDERLINES
CREATE TABLE orderlines (
    orderline_id SERIAL PRIMARY KEY,
    order_id INTEGER REFERENCES orders(order_id) ON DELETE CASCADE,
    cupcake_topping VARCHAR REFERENCES cupcake_tops(flavor),
    cupcake_bottom VARCHAR REFERENCES cupcake_bottoms(flavor),
    quantity INTEGER
);

-- 7. ORDER HISTORY
CREATE TABLE order_history (
    id SERIAL PRIMARY KEY,
    customer_id INTEGER REFERENCES customers(customer_id),
    status VARCHAR,
    order_date DATE
);

-- 8. ORDERLINE HISTORY
CREATE TABLE orderline_history (
    id SERIAL PRIMARY KEY,
    order_id INTEGER REFERENCES order_history(id),
    cupcake_topping VARCHAR,
    cupcake_bottom VARCHAR,
	cupcake_topping_price DOUBLE PRECISION,
	cupcake_bottom_price DOUBLE PRECISION,
	quantity INTEGER
);



INSERT INTO customers (email, name, password, balance)
VALUES
('john@gmail.com', 'John', 'password123', 500),
('anna@gmail.com', 'Anna', 'anna123', 350),
('peter@gmail.com', 'Peter', 'peter123', 200),
('sara@gmail.com', 'Sara', 'sara123', 750),
('mike@gmail.com', 'Mike', 'mike123', 100),
('ledut@ek.dk', 'Lukas', '1234', 1000),
('olga@ek.dk', 'Olga', '1234', 1000),
('alex@gmail.com', 'Alexander', '1234', 1000),
('magnus@gmail.com', 'Magnus', '1234', 1000);


-- =========================================
-- 2. ADMINS
-- =========================================

INSERT INTO admins (email, name, password)
VALUES
('admin@cupcake.dk', 'Admin User', 'admin123'),
('manager@cupcake.dk', 'Manager User', 'manager123');


-- =========================================
-- 3. TOPPINGS
-- =========================================

INSERT INTO cupcake_tops (flavor, price)
VALUES
('Chokolade', 10.00),
('Blåbær', 8.00),
('Hindbær', 9.00),
('Jordbær', 7.00),
('Rom/Rosin', 11.00),
('Applesin', 12.00),
('Citron', 13.00),
('Skimmelost', 9.00);


-- =========================================
-- 4. BOTTOMS
-- =========================================

INSERT INTO cupcake_bottoms (flavor, price)
VALUES
('Chokolade', 15.00),
('Vanilje', 18.00),
('Muskatnød', 12.00),
('Pistache', 5.00),
('Mandel', 20.00);


-- =========================================
-- 5. ORDERS
-- =========================================

INSERT INTO orders (customer_id, status)
VALUES
(1, 'CART'),
(2, 'PLACED'),
(3, 'APPROVED'),
(4, 'PLACED'),
(5, 'CART');


-- =========================================
-- 6. ORDERLINES
-- =========================================

INSERT INTO orderlines (order_id, cupcake_topping, cupcake_bottom, quantity)
VALUES
(1, 'Chokolade', 'Chokolade', 2),
(1, 'Jordbær', 'Vanilje', 1),
(2, 'Hindbær', 'Mandel', 2),
(3, 'Blåbær', 'Chokolade', 1),
(4, 'Blåbær', 'Pistache', 3),
(4, 'Blåbær', 'Vanilje', 1),
(5, 'Rom/Rosin', 'Vanilje', 1),
(5, 'Citron', 'Pistache', 2),
(5, 'Skimmelost', 'Muskatnød', 1);


-- =========================================
-- 7. ORDER HISTORY
-- =========================================

INSERT INTO order_history
(customer_id, status, order_date)
VALUES
(1, 'COMPLETED', '2026-11-01'),
(2, 'COMPLETED', '2026-11-03'),
(3, 'COMPLETED', '2026-11-04'),
(1, 'COMPLETED', '2026-11-04'),
(4, 'DENIED', '2026-11-05'),
(5, 'COMPLETED', '2026-11-07');


-- =========================================
-- 8. ORDERLINE HISTORY
-- =========================================

INSERT INTO orderline_history
(order_id, cupcake_topping, cupcake_bottom, cupcake_topping_price, cupcake_bottom_price, quantity)
VALUES
(1, 'Chokolade', 'Mandel', 10.00, 20.00, 2),
(1, 'Blåbær', 'Vanilje', 8.00, 18.00, 1),
(2, 'Chokolade', 'Mandel', 10.00, 20.00, 1),
(3, 'Blåbær', 'Pistache', 8.00, 5.00, 2),
(4, 'Skimmelost', 'Pistache', 9.00, 5.00, 3),
(5, 'Chokolade', 'Vanilje', 10.00, 18.00, 2),
(6, 'Skimmelost', 'Mandel', 9.00, 20.00, 1);

