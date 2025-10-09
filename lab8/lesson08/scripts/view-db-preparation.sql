DROP DATABASE IF EXISTS lesson08;
CREATE DATABASE lesson08;
use lesson08;

CREATE TABLE CustomerMaster (
    CustId VARCHAR(10) PRIMARY KEY,
    Name   VARCHAR(100)
);

INSERT INTO CustomerMaster (CustId, Name) VALUES
('C001', 'Customer 1'),
('C002', 'Customer 2'),
('C003', 'Customer 3'),
('C004', 'Customer 4'),
('C005', 'Customer 5');


CREATE TABLE ItemMaster (
    ItemId    VARCHAR(10) PRIMARY KEY,
    ItemName  VARCHAR(100)
);

INSERT INTO ItemMaster (ItemId, ItemName) VALUES
('I001', 'Item 1'),
('I002', 'Item 2'),
('I003', 'Item 3'),
('I004', 'Item 4'),
('I005', 'Item 5');


CREATE TABLE OrderMaster (
    OrdId   VARCHAR(10) PRIMARY KEY,
    CustId  VARCHAR(10),
    ItemId  VARCHAR(10),
    Qty     INT,
    Price   DECIMAL(10,2),
    FOREIGN KEY (CustId) REFERENCES CustomerMaster(CustId),
    FOREIGN KEY (ItemId) REFERENCES ItemMaster(ItemId)
);

INSERT INTO OrderMaster (OrdId, CustId, ItemId, Qty, Price) VALUES
('0001', 'C001', 'I001', 10, 125.50),
('0002', 'C002', 'I002', 15, 365.00),
('0003', 'C003', 'I003',  8, 251.00),
('0004', 'C004', 'I004', 12, 125.00),
('0005', 'C005', 'I005', 25, 165.00);
