BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Admin" (
	"AID"	INTEGER,
	PRIMARY KEY("AID"),
	FOREIGN KEY("AID") REFERENCES "User"("UID")
);
CREATE TABLE IF NOT EXISTS "Cashier" (
	"CAID"	INTEGER,
	PRIMARY KEY("CAID"),
	FOREIGN KEY("CAID") REFERENCES "User"("UID")
);
CREATE TABLE IF NOT EXISTS "Customer" (
	"CID"	INTEGER,
	PRIMARY KEY("CID"),
	FOREIGN KEY("CID") REFERENCES "Person"("ID") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "OrderItem" (
	"PID"	INTEGER,
	"OID"	INTEGER,
	"Desired_Quantity"	INTEGER NOT NULL CHECK("Desired_Quantity" > 0),
	"Total_Price"	REAL,
	PRIMARY KEY("PID","OID"),
	FOREIGN KEY("OID") REFERENCES "Orders"("ID") ON DELETE CASCADE,
	FOREIGN KEY("PID") REFERENCES "Product"("ID") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "Orders" (
	"ID"	INTEGER,
	"Date"	TEXT NOT NULL,
	"Discount"	REAL DEFAULT 0 CHECK("Discount" >= 0),
	"Payment_Method"	TEXT NOT NULL,
	"Total_Price"	REAL DEFAULT 0,
	"Calculated_Price"	REAL DEFAULT 0,
	"CID"	INTEGER,
	"UID"	INTEGER,
	PRIMARY KEY("ID"),
	FOREIGN KEY("CID") REFERENCES "Customer"("CID") ON DELETE SET NULL,
	FOREIGN KEY("UID") REFERENCES "User"("UID") ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS "Person" (
	"ID"	INTEGER,
	"Name"	TEXT NOT NULL,
	"Phone"	TEXT,
	"Email"	TEXT,
	"Type"	TEXT NOT NULL CHECK("Type" IN ('CUSTOMER', 'SUPPLIER', 'USER')),
	PRIMARY KEY("ID")
);
CREATE TABLE IF NOT EXISTS "Product" (
	"ID"	INTEGER,
	"Name"	TEXT NOT NULL,
	"Price"	REAL NOT NULL CHECK("Price" >= 0),
	"Quantity"	INTEGER NOT NULL CHECK("Quantity" >= 0),
	"Status"	TEXT NOT NULL DEFAULT 'Available',
	"SID"	INTEGER,
	"Colour"	TEXT,
	PRIMARY KEY("ID"),
	FOREIGN KEY("SID") REFERENCES "Supplier"("SID") ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS "Supplier" (
	"SID"	INTEGER,
	PRIMARY KEY("SID"),
	FOREIGN KEY("SID") REFERENCES "Person"("ID") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "User" (
	"UID"	INTEGER,
	"Email"	TEXT NOT NULL UNIQUE,
	"Password"	TEXT NOT NULL,
	"Salary"	REAL,
	PRIMARY KEY("UID"),
	FOREIGN KEY("UID") REFERENCES "Person"("ID") ON DELETE CASCADE
);
CREATE TRIGGER Calc_OrderItem_Total
AFTER INSERT ON OrderItem
FOR EACH ROW
BEGIN
    -- تحديث Total_Price للـ OrderItem
    UPDATE OrderItem
    SET Total_Price = NEW.Desired_Quantity * (SELECT Price FROM Product WHERE ID = NEW.PID)
    WHERE PID = NEW.PID AND OID = NEW.OID;

    -- تحديث Total_Price للـ Order
    UPDATE Orders
    SET Total_Price = (SELECT SUM(Total_Price) FROM OrderItem WHERE OID = NEW.OID)
    WHERE ID = NEW.OID;

    -- تحديث Calculated_Price
    UPDATE Orders
    SET Calculated_Price = Total_Price -( Total_Price * Discount / 100) 
    WHERE ID = NEW.OID;
END;
CREATE TRIGGER CheckPaymentMethod
BEFORE INSERT ON Orders
FOR EACH ROW
BEGIN
    SELECT
        CASE
            WHEN UPPER(NEW.Payment_Method) NOT IN ('CASH', 'CREDIT')
            THEN RAISE(ABORT, 'Invalid payment method! Must be Cash or Credit.')
        END;
END;
CREATE TRIGGER Check_Product_Stock
BEFORE INSERT ON OrderItem
FOR EACH ROW
BEGIN
    SELECT 
        CASE
            WHEN (SELECT Quantity FROM Product WHERE ID = NEW.PID) < NEW.Desired_Quantity
            THEN RAISE(ABORT, 'Not enough stock for this product!')
        END;
END;
CREATE TRIGGER Prevent_Duplicate_Person
BEFORE INSERT ON Person
FOR EACH ROW
BEGIN
    SELECT CASE
        WHEN EXISTS (
            SELECT 1 FROM Person 
            WHERE Name = NEW.Name 
              AND Email = NEW.Email 
              AND Phone = NEW.Phone
        )
        THEN RAISE(ABORT, 'This person already exists!')
    END;
END;
CREATE TRIGGER Prevent_ProductPrice_Change
BEFORE UPDATE OF Price ON Product
FOR EACH ROW
BEGIN
    SELECT
        CASE
            WHEN EXISTS (SELECT 1 FROM OrderItem WHERE PID = OLD.ID)
            THEN RAISE(ABORT, 'Cannot change product price after it has been ordered!')
        END;
END;
CREATE TRIGGER ReduceProductQuantity
AFTER INSERT ON OrderItem
FOR EACH ROW
BEGIN
    UPDATE Product
    SET Quantity = Quantity - NEW.Desired_Quantity
    WHERE ID = NEW.PID;
END;
CREATE TRIGGER UpdateProductStatus
AFTER UPDATE OF Quantity ON Product
FOR EACH ROW
BEGIN
    UPDATE Product
    SET Status = CASE 
                    WHEN NEW.Quantity = 0 THEN 'Unavailable'
                    ELSE 'Available'
                 END
    WHERE ID = NEW.ID;
END;
COMMIT;
