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

INSERT INTO USERS (UNAME, PASSWD, "ROLE") VALUES ('meaydin', 'aydinme', 'doctor');
INSERT INTO USERS (UNAME, PASSWD, "ROLE") VALUES ('eaydin', '12345me', 'nurse');
INSERT INTO USERS (UNAME, PASSWD, "ROLE") VALUES ('caidan', '5432@10', 'client');
INSERT INTO USERS (UNAME, PASSWD, "ROLE") VALUES ('princehassan', 'prince_passwd', 'client');
INSERT INTO USERS (UNAME, PASSWD, "ROLE") VALUES ('admin', 'admin_passwd', 'admin');

INSERT INTO EMPLOYEES (ENAME, EADDRESS, UNAME) VALUES ('Mehmet Aydin', 'Mehmets Address, London, NW4 0BH', 80, 'meaydin');
INSERT INTO EMPLOYEES (ENAME, EADDRESS, UNAME) VALUES ('Emin Aydin', 'Emiin''s Address, Bristol, BS16', 76, 'eaydin');

INSERT INTO CLIENTS (CNAME, CADDRESS, CTYPE, UNAME) VALUES ('Charly Aidan', '14 King Street, Aberdeen, AB24 1BR', 'NHS', 'caidan');
INSERT INTO CLIENTS (CNAME, CADDRESS, CTYPE, UNAME) VALUES ('Prince Hassan', 'Non-UK street, Non-UK Town, Non_UK', 'private', 'princehassan');

