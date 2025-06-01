USE hotel;

CREATE TABLE rooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    number INT NOT NULL,
    type VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    booked BOOLEAN NOT NULL DEFAULT 0,
    days_booked INT DEFAULT 0
);

CREATE TABLE food_orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL
);

CREATE TABLE room_food_orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room_id INT NOT NULL,
    food_order_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES rooms(id),
    FOREIGN KEY (food_order_id) REFERENCES food_orders(id)
);

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    contact_number VARCHAR(20),
    email VARCHAR(255),
    gender VARCHAR(10)
);

INSERT INTO rooms (number, type, price, booked, days_booked) VALUES
(101, 'Одномісний', 500.0, FALSE, 0),
(102, 'Двомісний', 800.0, FALSE, 0),
(103, 'Люкс', 1500.0, FALSE, 0),
(104, 'Одномісний', 500.0, FALSE, 0),
(105, 'Двомісний', 800.0, FALSE, 0),
(106, 'Одномісний', 500.0, FALSE, 0),
(107, 'Двомісний', 800.0, FALSE, 0),
(108, 'Люкс', 1500.0, FALSE, 0),
(109, 'Одномісний', 500.0, FALSE, 0),
(110, 'Двомісний', 800.0, FALSE, 0),
(111, 'Одномісний', 500.0, FALSE, 0),
(112, 'Двомісний', 800.0, FALSE, 0),
(113, 'Люкс', 1500.0, FALSE, 0),
(114, 'Одномісний', 500.0, FALSE, 0),
(115, 'Двомісний', 800.0, FALSE, 0),
(116, 'Одномісний', 500.0, FALSE, 0),
(117, 'Двомісний', 800.0, FALSE, 0),
(118, 'Люкс', 1500.0, FALSE, 0),
(119, 'Одномісний', 500.0, FALSE, 0),
(120, 'Двомісний', 800.0, FALSE, 0),
(121, 'Одномісний', 500.0, FALSE, 0),
(122, 'Двомісний', 800.0, FALSE, 0),
(123, 'Люкс', 1500.0, FALSE, 0),
(124, 'Одномісний', 500.0, FALSE, 0),
(125, 'Двомісний', 800.0, FALSE, 0),
(126, 'Одномісний', 500.0, FALSE, 0),
(127, 'Двомісний', 800.0, FALSE, 0),
(128, 'Люкс', 1500.0, FALSE, 0),
(129, 'Одномісний', 500.0, FALSE, 0),
(130, 'Двомісний', 800.0, FALSE, 0);

INSERT INTO food_orders (order_name, price) VALUES
('Піца', 100.0),
('Бургер', 80.0),
('Паста', 120.0),
('Салат', 60.0),
('Суші', 150.0),
('Стейк', 200.0),
('Сендвіч', 50.0),
('Тако', 70.0),
('Картопля фрі', 40.0),
('Суп', 90.0),
('Морозиво', 30.0),
('Торт', 45.0);

SELECT * FROM rooms;
SELECT * FROM food_orders;
SELECT * FROM room_food_orders;
SELECT * FROM customers;