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
	cDob date,
	cAddress varchar(100),
	cType varchar(10),
	uName varchar(20) references users(uname) on delete set null
);

create table employees(
	eID int not null primary key
            generated always as identity (start with 1, increment by 1), 
	eName varchar(50),
	eDob date,
	eAddress varchar(100),
	eRate float,
	uName varchar(20) references users(uname) on delete set null
);

create table schedule (
	sID int not null primary key
            generated always as identity (start with 1, increment by 1), 
    eID int references employees(eID) on delete set null,
    cID int references clients(cID) on delete set null,
	sType varchar(11),
	nSlot int default 0,
    sDate date,
    sTime time,
	cancelled boolean default false,
	description varchar(255)
);
-- nSlot=0 for surgery, nSlot will later be used to calculate billing.charge (nSlot * rate) for appointment
-- sType is either "appointment" or "surgery"


create table prescription (
	pID int not null primary key
            generated always as identity (start with 1, increment by 1), 
	eID int references employees(eID) on delete set null,
    cID int references clients(cID) on delete set null, 
	pDate date default null,		-- date issued
	pUse int default null,		-- number of uses
	pDescription varchar(255)
);


create table billing(
    bID int not null primary key
            generated always as identity (start with 1, increment by 1),
    sID int references schedule(sID) on delete set null,
    charge float,
	paid boolean default false
);
-- charge for appointment will be calculated, for surgery will be manually entered

-- in order, the passwords are 'nguyen', 'shen', 'dam', 'phan'
INSERT INTO APP.USERS VALUES('hue', '2X270C6Y6753M267AMKM5XMKZAKZ0363MYJAK7M9JKZ7CM29K020A2JC29JK20ZZ', 'admin', 'true', 'RUCS32I5OC4I8G0J67LGUI8PI18WYF6PA6CD3WFEM08CQZJ7VGIVPHHRQN0MPR1W');
INSERT INTO APP.USERS VALUES('biao', 'X59A59J33M0CZZ2M5YJXMYACY6CZ2KZ5J9JX33M3A6J07JA6FJ6ZJZ2AZFMXFCX0', 'doctor', 'true', 'MWTQDZEUFTXW918OJIMJDG0RD6PYH57YLFR6VH4ON776HET6VSSLK2WKJV214CHV');
INSERT INTO APP.USERS VALUES('au', 'ZCJ95622063953ZZA557K753KCX65XXYXKXJY6MJZKC22YX0M0A63ZJMXAFKCX70', 'nurse', 'true', 'KLBJ8E7FZ4RE6DEGEFT0PYRGE2ITJB6JQILOMIDSAL1D1CH74QOBGBE5HYPKCYHR');
INSERT INTO APP.USERS VALUES('phong', 'X95AX9FZC5Z706M0AZ7Z5YF9YY6K72CMC5M95C009C0FYAC2J75Y9622Z967CCY7', 'client', 'true', 'KGV6G240GU4KVU73QQ8FQOZAHVBF4MCB0RMOPEQJSB5HDJJ2FW2LO4VNXANMMR09');

INSERT INTO APP.EMPLOYEES (ename, edob, eaddress, erate, uname) VALUES ('Biao Shen', '1999-01-01', 'Some UK Address', 44, 'biao');
INSERT INTO APP.EMPLOYEES (ename, edob, eaddress, erate, uname) VALUES ('Au Dam', '1999-12-30', 'Bristol, England', 44, 'au');
INSERT INTO APP.Clients (cname, cdob, caddress, ctype, uname) VALUES ('Phong Phan', '1999-06-15', 'Bristol, UK', 'private', 'phong');

INSERT INTO APP.schedule (eid, cid, stype, nslot, sdate, stime, description) VALUES (1, 1, 'appointment', 1, '2021-01-10', '17:00', 'migrane');
INSERT INTO APP.schedule (eid, cid, stype, nslot, sdate, stime, description) VALUES (1, 1, 'appointment', 4, '2021-01-14', '09:00', 'migrane and dizzy');
INSERT INTO APP.schedule (eid, cid, stype, nslot, sdate, stime, description) VALUES (1, 1, 'surgery', 0, '2021-01-19', '17:00', 'small brain tumor');
INSERT INTO APP.schedule (eid, cid, stype, nslot, sdate, stime, description) VALUES (2, 1, 'appointment', 1, '2021-01-20', '11:00', 'post-op checkup');
INSERT INTO APP.schedule (eid, cid, stype, nslot, sdate, stime, description) VALUES (2, 1, 'appointment', 2, '2021-01-24', '15:00', 'post-op checkup');

INSERT INTO APP.billing (sid, charge, paid) VALUES (1, 22, true);
INSERT INTO APP.billing (sid, charge, paid) VALUES (2, 88, true);
INSERT INTO APP.billing (sid, charge, paid) VALUES (3, 2344, false);
INSERT INTO APP.billing (sid, charge, paid) VALUES (4, 22, false);
INSERT INTO APP.billing (sid, charge, paid) VALUES (5, 44, false);

INSERT INTO APP.prescription (eid, cid, pDate, pUse, pDescription) VALUES (1, 1, '2021-01-14', 2, 'Headache Pills');
INSERT INTO APP.prescription (eid, cid, pDescription) VALUES (1, 1, 'Headache Pills');
INSERT INTO APP.prescription (eid, cid, pDate, pUse, pDescription) VALUES (1, 1, '2021-01-21', 5, 'Post surgery pills');

