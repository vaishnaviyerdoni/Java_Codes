CREATE DATABASE InventoryDB;

USE InventoryDB;

CREATE TABLE InventoryTable(
	itemId INT PRIMARY KEY IDENTITY(1,1),
	itemName VARCHAR(80) NOT NULL,
	category VARCHAR(100),
	price DECIMAL(10,2),
	quantity INT NOT NULL,
	LowStockThreshold INT CHECK (LowStockThreshold >= 0)
)


CREATE TABLE Users(
	userId INT PRIMARY KEY IDENTITY(1,1),
	userName VARCHAR(100) UNIQUE NOT NULL,
	email VARCHAR(100) NOT NULL,
	passcode VARCHAR(100) NOT NULL,
	roleUser VARCHAR(50) CHECK (roleUser IN ('admin', 'customer', 'staff'))
)

CREATE TABLE Orders(
	orderId INT PRIMARY KEY IDENTITY(1,1),
	userId INT FOREIGN KEY REFERENCES Users(userId),
	orderDate DATE DEFAULT GETDATE(),
	customerName VARCHAR(100) NOT NULL,
	orderStatus VARCHAR(100) CHECK (orderStatus in('SHIPPED', 'DELIVERED', 'CANCELLED', 'PENDING'))
)

CREATE TABLE OrderItems(
    itemsId INT PRIMARY KEY IDENTITY(1,1),
    orderId INT FOREIGN KEY REFERENCES Orders(orderId),
    userId  INT FOREIGN KEY REFERENCES Users(userId),
    inventoryId INT FOREIGN KEY REFERENCES InventoryTable(itemId),
    quantity INT NOT NULL, 
    Subtotal DECIMAL(10,2)
);