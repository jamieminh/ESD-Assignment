create table users(
	uname varchar(20) primary key,
	passwd char(64) not null,	-- hashed password SHA-256
	role varchar(10),	
	authorized boolean default false,
	token char(64) not null		-- to login user using cookie
);

create table clients(
	cID int not null primary key
            generated always as identity (start with 1, increment by 1), 
	cName varchar(50),
	cAddress varchar(100),
	cType varchar(10),
	uName varchar(20) references users(uname) on delete cascade
);

create table employees(
	eID int not null primary key
            generated always as identity (start with 1, increment by 1), 
	eName varchar(50),
	eAddress varchar(100),
	eRate float,
	uName varchar(20) references users(uname) on delete cascade
);

create table schedule (
	sID int not null primary key
            generated always as identity (start with 1, increment by 1), 
    eID int references employees(eID),
    cID int references clients(cID),
	sType varchar(11),
	nSlot int default 0,
    sDate date,
    sTime time,
	cancelled boolean default false
);
-- nSlot=0 for surgery, nSlot will later be used to calculate billing.charge (nSlot * rate) for appointment
-- sType is either "appointment" or "surgery"

create table billing(
    bID int not null primary key
            generated always as identity (start with 1, increment by 1),
    sID int references schedule(sID) on delete cascade,
    charge float
);
-- charge for appointment will be calculated, for surgery will be manually entered

-- in order, the passwords are 'nguyen', 'shen', 'dam', 'phan'
INSERT INTO APP.USERS VALUES('hue', '2X270C6Y6753M267AMKM5XMKZAKZ0363MYJAK7M9JKZ7CM29K020A2JC29JK20ZZ', 'admin', 'true', 'RUCS32I5OC4I8G0J67LGUI8PI18WYF6PA6CD3WFEM08CQZJ7VGIVPHHRQN0MPR1W');
INSERT INTO APP.USERS VALUES('biao', 'X59A59J33M0CZZ2M5YJXMYACY6CZ2KZ5J9JX33M3A6J07JA6FJ6ZJZ2AZFMXFCX0', 'doctor', 'true', 'MWTQDZEUFTXW918OJIMJDG0RD6PYH57YLFR6VH4ON776HET6VSSLK2WKJV214CHV');
INSERT INTO APP.USERS VALUES('au', 'ZCJ95622063953ZZA557K753KCX65XXYXKXJY6MJZKC22YX0M0A63ZJMXAFKCX70', 'nurse', 'true', 'KLBJ8E7FZ4RE6DEGEFT0PYRGE2ITJB6JQILOMIDSAL1D1CH74QOBGBE5HYPKCYHR');
INSERT INTO APP.USERS VALUES('phong', 'X95AX9FZC5Z706M0AZ7Z5YF9YY6K72CMC5M95C009C0FYAC2J75Y9622Z967CCY7', 'client', 'true', 'KGV6G240GU4KVU73QQ8FQOZAHVBF4MCB0RMOPEQJSB5HDJJ2FW2LO4VNXANMMR09');

INSERT INTO APP.EMPLOYEES (ename, eaddress, erate, uname) VALUES ('Biao Shen', 'Some UK Address', 44, 'biao');
INSERT INTO APP.EMPLOYEES (ename, eaddress, erate, uname) VALUES ('Au Dam', 'Bristol, England', 44, 'au');
INSERT INTO APP.Clients (cname, caddress, ctype, uname) VALUES ('Phong Phan', 'Bristol, UK', 'private', 'phong');