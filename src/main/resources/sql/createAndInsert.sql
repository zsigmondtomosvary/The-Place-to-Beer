--Create Enums
CREATE TYPE IF NOT EXISTS taxclass AS ENUM ('10% Ermäßigt', '20% Normal');
--Create Producttable
CREATE TABLE IF NOT EXISTS products(
  productid int NOT NULL AUTO_INCREMENT,
  productname varchar(30) NOT NULL,
  text varchar(50),
  netto double NOT NULL,
  brutto double,
  addeddate TIMESTAMP,
  lastedited TIMESTAMP,
  category varchar NOT NULL,
  taxclass varchar NOT NULL,
  PRIMARY KEY (productid)
);
--Create Invoicetable
CREATE TABLE IF NOT EXISTS invoices(
 invoiceid int NOT NULL AUTO_INCREMENT,
 tablenumber int NOT NULL,
 invoicedate TIMESTAMP,
 billdate TIMESTAMP ,
 status VARCHAR (30),
 products VARCHAR ,
 billsum double,
 taxsum varchar,
 paymenttype varchar,
 PRIMARY KEY(invoiceid)
);
--Create Productorders
CREATE TABLE IF NOT EXISTS orderproduct(
  pid int NOT NULL,
  iid int NOT NULL,
  FOREIGN KEY (pid) REFERENCES products(productid),
  FOREIGN KEY (iid) REFERENCES invoices(invoiceid),
  PRIMARY KEY (pid, iid)
 );

-- Insert Drinks
INSERT INTO products VALUES (default ,'Sparkling Water', '', '2.2', '2.42', current_timestamp, current_timestamp , 'Alkoholfreies Getranke', '10% Ermäßigt');
INSERT INTO products VALUES (default ,'Still Water', '', '2.2', '2.42',current_timestamp, current_timestamp , 'Alkoholfreies Getranke', '10% Ermäßigt');
INSERT INTO products VALUES (default ,'Coca Cola', '', '2.9', '3.19',current_timestamp, current_timestamp , 'Alkoholfreies Getranke', '10% Ermäßigt');
INSERT INTO products VALUES (default ,'Almdudler', '', '2.9', '3.19',current_timestamp, current_timestamp , 'Alkoholfreies Getranke', '10% Ermäßigt');
INSERT INTO products VALUES (default ,'Espresso', '', '1.5', '1.8',current_timestamp, current_timestamp , 'Heisse Getranke', '20% Normal');
INSERT INTO products VALUES (default ,'Cafe Latte', '', '2.9', '3.48',current_timestamp, current_timestamp , 'Heisse Getranke', '20% Normal');
INSERT INTO products VALUES (default ,'Cappucino', '', '2.5', '3',current_timestamp, current_timestamp , 'Heisse Getranke', '20% Normal');
INSERT INTO products VALUES (default ,'Tee', '', '1.5', '1.8',current_timestamp, current_timestamp , 'Heisse Getranke', '20% Normal');
INSERT INTO products VALUES (default ,'Ottakringer', 'It is an austrian beer', '3.3', '3.96',current_timestamp, current_timestamp , 'Alkoholisches Getranke', '20% Normal');
INSERT INTO products VALUES (default ,'Budweiser', 'It is an czech beer', '3.6', '4.32',current_timestamp, current_timestamp , 'Alkoholisches Getranke', '20% Normal');
INSERT INTO products VALUES (default ,'Red Wine', '1 dl', '3.9', '4.68',current_timestamp, current_timestamp , 'Alkoholisches Getranke', '20% Normal');
INSERT INTO products VALUES (default ,'White Wine', '1 dl', '3.9', '4.68',current_timestamp, current_timestamp , 'Alkoholisches Getranke', '20% Normal');

-- Insert Food
INSERT INTO products VALUES (default ,'Chicken Soup', '', '3', '3.3',current_timestamp, current_timestamp , 'Vorspeise', '10% Ermäßigt');
INSERT INTO products VALUES (default ,'Schnitzel with Potato Fries', 'It is an austrian food', '8', '8.8',current_timestamp, current_timestamp, 'Hauptgericht', '10% Ermäßigt');
INSERT INTO products VALUES (default ,'Margareta Pizza', '', '7', '8.4',current_timestamp, current_timestamp , 'Hauptgericht', '20% Normal');
INSERT INTO products VALUES (default ,'Kaiserschmarn', 'It is an austrian desert', '5', '6',current_timestamp, current_timestamp , 'Dessert', '20% Normal');

-- Insert Invoices
INSERT INTO invoices VALUES (default, 1, current_timestamp, current_timestamp, 'Closed', 'Still Water 2.42', 2.42, '10% Ermäßigt 0.22', 'Card');
INSERT INTO invoices VALUES (default, 2, current_timestamp, current_timestamp, 'Closed', 'Chicken Soup 3.3', 3.3, '10% Ermäßigt 0.3', 'Card');
INSERT INTO invoices VALUES (default, 3, current_timestamp, current_timestamp, 'Closed', 'Still Water 2.42', 2.42, '10% Ermäßigt 0.22', 'Cash');
INSERT INTO invoices VALUES (default, 4, current_timestamp, current_timestamp, 'Closed', 'Still Water 2.42', 2.42, '10% Ermäßigt 0.22', 'Card');
INSERT INTO invoices VALUES (default, 5, current_timestamp, current_timestamp, 'Closed', 'Still Water 2.42', 2.42, '10% Ermäßigt 0.22', 'Cash');
INSERT INTO invoices VALUES (default, 6, current_timestamp, current_timestamp, 'Closed', 'Kaiserschmarn 5.0', 5.0, '20% Normal 1.0', 'Card');
INSERT INTO invoices VALUES (default, 7, current_timestamp, current_timestamp, 'Closed', 'Still Water 2.42', 2.42, '10% Ermäßigt 0.22', 'Card');
INSERT INTO invoices VALUES (default, 8, current_timestamp, current_timestamp, 'Closed', 'Still Water 2.42', 2.42, '10% Ermäßigt 0.22', 'Cash');
INSERT INTO invoices VALUES (default, 9, current_timestamp, current_timestamp, 'Closed', 'Still Water 2.42', 2.42, '10% Ermäßigt 0.22', 'Card');
INSERT INTO invoices VALUES (default, 10, current_timestamp, current_timestamp, 'Closed', 'Still Water 2.42', 2.42, '10% Ermäßigt 0.22', 'Card');
INSERT INTO invoices VALUES (default, 1, current_timestamp, null, 'Open', '', 0, '', '');
INSERT INTO invoices VALUES (default, 2, current_timestamp, null, 'Open', '', 0, '', '');
INSERT INTO invoices VALUES (default, 3, current_timestamp, null, 'Open', '', 0, '', '');
INSERT INTO invoices VALUES (default, 4, current_timestamp, null, 'Open', '', 0, '', '');
INSERT INTO invoices VALUES (default, 5, current_timestamp, null, 'Open', '', 0, '', '');


-- Insert OrderProduct
INSERT INTO orderproduct VALUES (1,11);
INSERT INTO orderproduct VALUES (5,12);
INSERT INTO orderproduct VALUES (2,12);
INSERT INTO orderproduct VALUES (9,12);
INSERT INTO orderproduct VALUES (8,13);
INSERT INTO orderproduct VALUES (6,13);
INSERT INTO orderproduct VALUES (5,14);
INSERT INTO orderproduct VALUES (11,14);
INSERT INTO orderproduct VALUES (1,15);
INSERT INTO orderproduct VALUES (2,15);