use lesson08;

DROP VIEW IF EXISTS OrderCustomerItemView;

CREATE VIEW OrderCustomerItemView AS
SELECT 
    o.ordId,
    c.CustId AS Custld,
    c.Name AS Name,
    i.ItemID AS ItemId,
    i.ItemName AS "Item Name",
    o.Qty AS Qty,
    o.Price 
FROM 
    ordermaster o
JOIN 
    customermaster c ON o.CustId = c.CustId
JOIN 
    itemmaster i ON o.ItemID = i.ItemID;
    
    
-- Simple SELECT
SELECT * FROM OrderCustomerItemView;

-- With WHERE clause
SELECT * FROM OrderCustomerItemView WHERE Price > 200;

-- With ORDER BY
SELECT * FROM OrderCustomerItemView ORDER BY Qty DESC;

-- Update View Section -----------------
INSERT INTO CustomerMaster (CustId, Name) 
VALUES ('C006', 'Customer 6');

INSERT INTO ItemMaster (ItemId, ItemName) 
VALUES ('I006', 'Item 6');

INSERT INTO OrderMaster (OrdId, CustId, ItemId, Qty, Price) 
VALUES ('0006', 'C006', 'I006', 20, 199.99);

