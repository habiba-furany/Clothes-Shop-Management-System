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
	FOREIGN KEY("CID") REFERENCES "Person"("ID")
);
CREATE TABLE IF NOT EXISTS "OrderItem" (
	"PID"	INTEGER,
	"OID"	INTEGER,
	"Desired_Quantity"	INTEGER NOT NULL,
	"Total_Price"	REAL,
	PRIMARY KEY("PID","OID"),
	FOREIGN KEY("OID") REFERENCES "Orders"("ID"),
	FOREIGN KEY("PID") REFERENCES "Product"("ID")
);
CREATE TABLE IF NOT EXISTS "Orders" (
	"ID"	INTEGER,
	"Date"	TEXT NOT NULL,
	"Discount"	REAL,
	"Payment_Method"	TEXT CHECK("Payment_Method" IN ('CASH', 'CREDIT')),
	"Calculated_Price"	REAL,
	"Total_Price"	REAL,
	"CID"	INTEGER,
	"CAID"	INTEGER,
	PRIMARY KEY("ID"),
	FOREIGN KEY("CAID") REFERENCES "Cashier"("CAID"),
	FOREIGN KEY("CID") REFERENCES "Customer"("CID")
);
CREATE TABLE IF NOT EXISTS "Person" (
	"ID"	INTEGER,
	"Name"	TEXT NOT NULL,
	"Contact_Info"	TEXT,
	"Type"	TEXT NOT NULL,
	PRIMARY KEY("ID")
);
CREATE TABLE IF NOT EXISTS "Product" (
	"ID"	INTEGER,
	"Name"	TEXT NOT NULL,
	"Price"	REAL NOT NULL,
	"Quantity"	INTEGER NOT NULL,
	"Status"	TEXT,
	"SID"	INTEGER,
	"Colour"	TEXT,
	PRIMARY KEY("ID"),
	FOREIGN KEY("SID") REFERENCES "Supplier"("SID")
);
CREATE TABLE IF NOT EXISTS "Supplier" (
	"SID"	INTEGER,
	PRIMARY KEY("SID"),
	FOREIGN KEY("SID") REFERENCES "Person"("ID")
);
CREATE TABLE IF NOT EXISTS "User" (
	"UID"	INTEGER,
	"Email"	TEXT,
	"Password"	TEXT,
	"Type"	TEXT,
	"Salary"	REAL,
	PRIMARY KEY("UID"),
	FOREIGN KEY("UID") REFERENCES "Person"("ID")
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
CREATE TRIGGER Prevent_Duplicate_User
BEFORE INSERT ON User
FOR EACH ROW
BEGIN
    SELECT CASE
        WHEN EXISTS (
            SELECT 1 FROM User
            WHERE Email = NEW.Email
        )
        THEN RAISE(ABORT, 'User with this Email already exists!')
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
CREATE TRIGGER auto_insert_from_person
AFTER INSERT ON Person
BEGIN
    INSERT INTO Customer (CID)
    SELECT NEW.ID
    WHERE NEW.Type = 'CUSTOMER';
    INSERT INTO Supplier (SID)
    SELECT NEW.ID
    WHERE NEW.Type = 'SUPPLIER';
    INSERT INTO User (UID)
    SELECT NEW.ID
    WHERE NEW.Type = 'USER';

END;
CREATE TRIGGER auto_user_type_update
AFTER UPDATE OF Type ON User
BEGIN
    INSERT INTO Admin (AID)
    SELECT NEW.UID
    WHERE NEW.Type = 'ADMIN';
    INSERT INTO Cashier (CAID)
    SELECT NEW.UID
    WHERE NEW.Type ='CASHIER';
END;
COMMIT;
