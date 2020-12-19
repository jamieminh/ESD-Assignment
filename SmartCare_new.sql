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

INSERT INTO APP.USERS VALUES('hue', '2X270C6Y6753M267AMKM5XMKZAKZ0363MYJAK7M9JKZ7CM29K020A2JC29JK20ZZ', 'admin', 'true', 'RUCS32I5OC4I8G0J67LGUI8PI18WYF6PA6CD3WFEM08CQZJ7VGIVPHHRQN0MPR1W');
-- the password is 'nguyen'
