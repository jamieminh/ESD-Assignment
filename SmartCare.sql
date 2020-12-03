create table users(
	uname varchar(20) primary key,
	passwd varchar(20),
	role varchar(10)
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
	uName varchar(20) references users(uname) on delete cascade
);

create table operations(
    oID int not null primary key
            generated always as identity (start with 1, increment by 1), 
    eID int references employees(eID),
    cID int references clients(cID),
    oDate date,
    oTime time,
    nSlot int,
    charge float,
	ongoing boolean default true
);


create table booking_slots(
    sID int not null primary key
            generated always as identity (start with 1, increment by 1),
    eID int references employees(eID) on delete no action,
    cID int references clients(cID) on delete no action,
    sDate date,
    sTime time,
	ongoing boolean default true
);

INSERT INTO USERS (UNAME, PASSWD, "ROLE") VALUES ('meaydin', 'aydinme', 'doctor');
INSERT INTO USERS (UNAME, PASSWD, "ROLE") VALUES ('eaydin', '12345me', 'nurse');
INSERT INTO USERS (UNAME, PASSWD, "ROLE") VALUES ('caidan', '5432@10', 'client');
INSERT INTO USERS (UNAME, PASSWD, "ROLE") VALUES ('princehassan', 'prince_passwd', 'client');
INSERT INTO USERS (UNAME, PASSWD, "ROLE") VALUES ('admin', 'admin_passwd', 'admin');

INSERT INTO EMPLOYEES (ENAME, EADDRESS, UNAME) VALUES ('Mehmet Aydin', 'Mehmets Address, London, NW4 0BH', 'meaydin');
INSERT INTO EMPLOYEES (ENAME, EADDRESS, UNAME) VALUES ('Emin Aydin', 'Emiin''s Address, Bristol, BS16', 'eaydin');

INSERT INTO CLIENTS (CNAME, CADDRESS, CTYPE, UNAME) VALUES ('Charly Aidan', '14 King Street, Aberdeen, AB24 1BR', 'NHS', 'caidan');
INSERT INTO CLIENTS (CNAME, CADDRESS, CTYPE, UNAME) VALUES ('Prince Hassan', 'Non-UK street, Non-UK Town, Non_UK', 'private', 'princehassan');

