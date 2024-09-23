# brokage-firm

Running the Project
Clone the Repository: Clone or create the above directory structure in your local environment.

Build the Project: Use Maven to build the project.

bash
Kodu kopyala
mvn clean install
Run the Application: You can run the application using your IDE or through the command line:

bash
Kodu kopyala
mvn spring-boot:run
Access H2 Console: Open your browser and navigate to http://localhost:8080/h2-console. Use the default settings (JDBC URL: jdbc:h2:mem:testdb, User Name: sa, Password: password).
    Testing the API
You can use Postman or any other API client to test the following endpoints:

Create Order: POST /api/orders
List Orders: GET /api/orders?customerId=customer1&startDate=...&endDate=...
Delete Order: DELETE /api/orders/{id}?customerId=customer1
Deposit Money: POST /api/assets/deposit?customerId=customer1&amount=1000
Withdraw Money: POST /api/assets/withdraw?customerId=customer1&amount=500&iban=...
List Assets: GET /api/assets?customerId=customer1
Login: POST /api/auth/login?username=customer1&password=customer123
-------------------------------------------
  -- Create Customer Table
CREATE TABLE Customer (
    id VARCHAR(255) PRIMARY KEY,
    usable_size DOUBLE NOT NULL
);

-- Create Asset Table
CREATE TABLE Asset (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(255) NOT NULL,
    asset_name VARCHAR(255) NOT NULL,
    size INT NOT NULL,
    usable_size DOUBLE NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- Create Order Table
CREATE TABLE `Order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(255) NOT NULL,
    asset_name VARCHAR(255) NOT NULL,
    order_side ENUM('BUY', 'SELL') NOT NULL,
    size INT NOT NULL,
    price DOUBLE NOT NULL,
    status ENUM('PENDING', 'MATCHED', 'CANCELED') NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- Create User Table for Authentication
CREATE TABLE User (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

--------------------------------------------

Explanation of Each Table
Customer Table:

id: Unique identifier for each customer.
usable_size: Represents the available funds for the customer in TRY.
Asset Table:

id: Unique identifier for each asset record.
customer_id: Foreign key linking to the Customer table.
asset_name: The name of the stock asset.
size: Total number of shares owned by the customer.
usable_size: Number of shares that can currently be used for transactions.
Order Table:

id: Unique identifier for each order.
customer_id: Foreign key linking to the Customer table.
asset_name: The name of the asset related to the order.
order_side: Indicates whether the order is a BUY or SELL.
size: Number of shares requested in the order.
price: Price per share in TRY.
status: Status of the order (PENDING, MATCHED, CANCELED).
create_date: Timestamp for when the order was created.
Running the SQL Script
You can run this SQL script in the H2 database console after starting your Spring Boot application. To access the console:

Navigate to http://localhost:8080/h2-console.
Use the JDBC URL jdbc:h2:mem:testdb with the username sa and password password.
Paste the SQL script into the console and execute it to create the tables.
----------------------------------------------
INSERT INTO Customer (id, usable_size) VALUES
('CUST001', 10000.00),
('CUST002', 5000.00),
('CUST003', 15000.00),
('CUST004', 2000.00),
('CUST005', 8000.00);

-- Insert Sample Assets
INSERT INTO Asset (customer_id, asset_name, size, usable_size) VALUES
('CUST001', 'AAPL', 50, 50),
('CUST002', 'GOOGL', 30, 30),
('CUST003', 'TSLA', 20, 20),
('CUST004', 'AMZN', 15, 15);

-- Insert Sample Orders
INSERT INTO `Order` (customer_id, asset_name, order_side, size, price, status) VALUES
('CUST001', 'AAPL', 'BUY', 10, 150.00, 'PENDING'),
('CUST002', 'GOOGL', 'SELL', 5, 2000.00, 'PENDING'),
('CUST003', 'TSLA', 'BUY', 2, 600.00, 'MATCHED');

-- Insert Admin User for Authentication
INSERT INTO User (username, password, role) VALUES
('admin', 'admin123', 'ADMIN');
