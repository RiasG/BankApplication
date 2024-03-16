DROP TABLE IF EXISTS bank_transactions;
DROP TABLE IF EXISTS bank_user;


CREATE TABLE bank_user
(
    user_id SERIAL,
    user_name varchar(50) not null,
	user_password varchar(50) not null,
	user_current_balance decimal(10),
    PRIMARY KEY (user_id)
);

CREATE TABLE bank_transactions
(
	trans_id SERIAL,
	trans_amount decimal(10),
	trans_date date not null,
	trans_type varchar,
	trans_user_id integer not null,
	FOREIGN KEY (trans_user_id) REFERENCES bank_user(user_id) ON DELETE RESTRICT
);