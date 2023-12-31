

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance numeric(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

 CREATE SEQUENCE seq_transfer_id
      INCREMENT BY 1
      START WITH 3001
      NO MAXVALUE;

    CREATE TABLE transfer (
    	transfer_id int NOT NULL DEFAULT nextval('seq_transfer_id'),
    	transfer_amount numeric(13, 2) NOT NULL,
    	transfer_from int NOT NULL,
    	transfer_to int NOT NULL,
		CONSTRAINT PK_transfer PRIMARY KEY (transfer_id),
		CONSTRAINT FK_transfer_tenmo_user FOREIGN KEY (transfer_from) REFERENCES tenmo_user (username),
		CONSTRAINT FK_transfer_tenmouser_ FOREIGN KEY (transfer_to) REFERENCES tenmo_user (username)
		);


		CREATE TABLE status (
		status_id seral NOT NULL,
		transfer_id int NOT NULL,
	    status_desc varchar(200) NOT NULL,
    CONSTRAINT PK_request PRIMARY KEY (status_id),
	CONSTRAINT FK_status_transfer FOREIGN KEY (transfer_id) REFERENCES transfer (transfer_id)
		);

--COMMIT;

INSERT INTO tenmo_user (username, password_hash)
VALUES ('bob', '$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2'),
       ('user', '$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAEy'),
       ('Charlie', "paaaaaasssssswwwwwoooooorrrrrdddddd");

INSERT INTO account (user_id, balance)
VALUES(1003, 0),
VALUES(1002, 1000),
VALUES(1001, 5000);

INSERT INTO tenmo_user (username, password_hash)
VALUES ('bob', '$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2'),
       ('user', '$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAEy'),
       ('Charlie', "paaaaaasssssswwwwwoooooorrrrrdddddd")

INSERT INTO account (user_id, balance)
VALUES(1003, 5000)

INSERT INTO account (user_id, balance)
VALUES(1002, 1000)

INSERT INTO account (user_id, balance)
VALUES(1001, 0)

INSERT INTO account (user_id, balance)
VALUES (1001, 5000),
       (1002, 1000);

INSERT INTO transfer (transfer_id, transfer_amount, transfer_from, transfer_to)
VALUES (3002, 10, 'bob', 'user'),
(3003, 150, 'bob', 'user');

COMMIT;