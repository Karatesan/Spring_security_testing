CREATE DATABASE springsecurity;

--CONSTRAINT loan_customer_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
-- ==
--customer_id INT REFERENCES customer (customer_id) ON DELETE CASCADE
--Constraint daje mi dodatkowo mozliwosc nazwania relacji

CREATE TABLE IF NOT EXISTS customer
(
    customer_id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email    VARCHAR(100)  NOT NULL,
    mobile_number varchar(20) NOT NULL,
    password VARCHAR(500) NOT NULL,
    role     VARCHAR(45)  NOT NULL,
    create_date date DEFAULT NULL
);

INSERT INTO customer(name,email,mobile_number,password,role,create_date)
VALUES (
'Maciek',
'karatesan00@gmail.com',
'+48 663 342 268',
        '$2a$12$ApjIPYJVUjZSnbvbUfo/Uumbrf4NmldSCmmmV5Q31Xag6iJR.I8Dy',
        'admin',
        CURRENT_DATE
        );

CREATE TABLE IF NOT EXISTS accounts(
    customer_id BIGINT NOT NULL,
    account_number VARCHAR(20) NOT NULL PRIMARY KEY,
    account_type VARCHAR(100) NOT NULL,
    branch_address VARCHAR(200) NOT NULL,
    create_date date DEFAULT NULL,
    --nazwa relacji
    --FOREIGN KEY (customer_id) - oznacza, ze customer_id jest foreign key
    --REFERENCES customer (customer_id) - nasz foreign key jest customer_id z tabeli customer
    --ON DELETE CASCADE - jak usune customera to usuwa wsystko z accounts
    CONSTRAINT customer_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);

INSERT INTO accounts(customer_id,account_number,account_type,branch_address,create_date )
VALUES(
    1, '1234124123123123','Savings','123 Main Street, New York', CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS account_transactions (
    transaction_id VARCHAR(200) PRIMARY KEY NOT NULL,
    account_number VARCHAR(20) NOT NULL,
    customer_id BIGINT NOT NULL,
    transaction_date DATE NOT NULL,
    transaction_summary VARCHAR(200) NOT NULL,
    transaction_type VARCHAR(100) NOT NULL,
    transaction_amt INT NOT NULL,
    closing_balance INT NOT NULL,
    created_date DATE DEFAULT NULL,
    --INDEX customer_id (customer_id),
    --INDEX account_number (account_number),
    CONSTRAINT accounts_ibfk_2 FOREIGN KEY (account_number) REFERENCES accounts (account_number) ON DELETE CASCADE,
    CONSTRAINT acct_user_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);
--zeby moc korzystac z funkcji uuid
create extension if not exists "uuid-ossp";

--INTERVAL mowi ze odejmujem yczy dodajem jakis okres czasu i p tym dajemy np '5 days'
INSERT INTO account_transactions (transaction_id, account_number, customer_id, transaction_date, transaction_summary, transaction_type, transaction_amt, closing_balance, created_date)
VALUES (gen_random_uuid(), 1234124123123123, 1, CURRENT_DATE - INTERVAL '7 days', 'Coffee Shop', 'Withdrawal', 30, 34500, CURRENT_DATE - INTERVAL '7 days');

INSERT INTO account_transactions (transaction_id, account_number, customer_id, transaction_date, transaction_summary, transaction_type, transaction_amt, closing_balance, created_date)
VALUES (gen_random_uuid(), 1234124123123123, 1, CURRENT_DATE - INTERVAL '6 days', 'Uber', 'Withdrawal', 100, 34400, CURRENT_DATE - INTERVAL '6 days');

INSERT INTO account_transactions (transaction_id, account_number, customer_id, transaction_date, transaction_summary, transaction_type, transaction_amt, closing_balance, created_date)
VALUES (gen_random_uuid(), 1234124123123123, 1, CURRENT_DATE - INTERVAL '5 days', 'Self Deposit', 'Deposit', 500, 34900, CURRENT_DATE - INTERVAL '5 days');

INSERT INTO account_transactions (transaction_id, account_number, customer_id, transaction_date, transaction_summary, transaction_type, transaction_amt, closing_balance, created_date)
VALUES (gen_random_uuid(), 1234124123123123, 1, CURRENT_DATE - INTERVAL '4 days', 'Ebay', 'Withdrawal', 600, 34300, CURRENT_DATE - INTERVAL '4 days');

INSERT INTO account_transactions (transaction_id, account_number, customer_id, transaction_date, transaction_summary, transaction_type, transaction_amt, closing_balance, created_date)
VALUES (gen_random_uuid(), 1234124123123123, 1, CURRENT_DATE - INTERVAL '2 days', 'OnlineTransfer', 'Deposit', 700, 35000, CURRENT_DATE - INTERVAL '2 days');

INSERT INTO account_transactions (transaction_id, account_number, customer_id, transaction_date, transaction_summary, transaction_type, transaction_amt, closing_balance, created_date)
VALUES (gen_random_uuid(), 1234124123123123, 1, CURRENT_DATE - INTERVAL '1 day', 'Amazon.com', 'Withdrawal', 100, 34900, CURRENT_DATE - INTERVAL '1 day');

CREATE TABLE IF NOT EXISTS loans(
loan_number bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
customer_id BIGINT NOT NULL,
start_date date NOT NULL,
loan_type VARCHAR(100) NOT NULL,
total_loan INT NOT NULL,
amount_paid INT NOT NULL,
outstanding_amount INT NOT NULL,
create_date date DEFAULT NULL,
--te delety oznaczaja co sie dzeije z tym rekordem jezely referencowany parent rekord jest usuwany
CONSTRAINT loan_customer_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);

INSERT INTO loans (customer_id, start_date, loan_type, total_loan, amount_paid, outstanding_amount, create_date)
VALUES (1, '2020-10-13', 'Home', 200000, 50000, 150000, '2020-10-13');

INSERT INTO loans (customer_id, start_date, loan_type, total_loan, amount_paid, outstanding_amount, create_date)
VALUES (1, '2020-06-06', 'Vehicle', 40000, 10000, 30000, '2020-06-06');

INSERT INTO loans (customer_id, start_date, loan_type, total_loan, amount_paid, outstanding_amount, create_date)
VALUES (1, '2018-02-14', 'Home', 50000, 10000, 40000, '2018-02-14');

INSERT INTO loans (customer_id, start_date, loan_type, total_loan, amount_paid, outstanding_amount, create_date)
VALUES (1, '2018-02-14', 'Personal', 10000, 3500, 6500, '2018-02-14');

CREATE TABLE IF NOT EXISTS cards(
card_id bigint GENERATED ALWAYS AS IDENTITY primary key,
card_number varchar not null,
customer_id bigint references customer (customer_id) on delete cascade,
card_type varchar(100) not null,
total_limit int not null,
amount_used int not null,
available_amount int not null,
created_date date default null
);

INSERT INTO cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, created_date)
VALUES ('4565XXXX4656', 1, 'Credit', 10000, 500, 9500, CURRENT_DATE);

INSERT INTO cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, created_date)
VALUES ('3455XXXX8673', 1, 'Credit', 7500, 600, 6900, CURRENT_DATE);

INSERT INTO cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, created_date)
VALUES ('2359XXXX9346', 1, 'Credit', 20000, 4000, 16000, CURRENT_DATE);



CREATE TABLE IF NOT EXISTS notice_details(
notice_id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
notice_summary varchar(200) not null,
notice_details varchar(500) not null,
notic_beg_date date not null,
notic_end_date date DEFAULT null,
create_date date default null,
update_date date default null
);

INSERT INTO notice_details (notice_summary, notice_details, notic_beg_date, notic_end_date, create_date, update_date)
VALUES (
    'Home Loan Interest rates reduced',
    'Home loan interest rates are reduced as per the government guidelines. The updated rates will be effective immediately',
    CURRENT_DATE - INTERVAL '30 days',
    CURRENT_DATE + INTERVAL '30 days',
    CURRENT_DATE,
    NULL
);

INSERT INTO notice_details (notice_summary, notice_details, notic_beg_date, notic_end_date, create_date, update_date)
VALUES (
    'Net Banking Offers',
    'Customers who will opt for Internet banking while opening a saving account will get a $50 amazon voucher',
    CURRENT_DATE - INTERVAL '30 days',
    CURRENT_DATE + INTERVAL '30 days',
    CURRENT_DATE,
    NULL
);

INSERT INTO notice_details (notice_summary, notice_details, notic_beg_date, notic_end_date, create_date, update_date)
VALUES (
    'Mobile App Downtime',
    'The mobile application of the EazyBank will be down from 2AM-5AM on 12/05/2020 due to maintenance activities',
    CURRENT_DATE - INTERVAL '30 days',
    CURRENT_DATE + INTERVAL '30 days',
    CURRENT_DATE,
    NULL
);

INSERT INTO notice_details (notice_summary, notice_details, notic_beg_date, notic_end_date, create_date, update_date)
VALUES (
    'E Auction notice',
    'There will be an e-auction on 12/08/2020 on the Bank website for all the stubborn arrears. Interested parties can participate in the e-auction',
    CURRENT_DATE - INTERVAL '30 days',
    CURRENT_DATE + INTERVAL '30 days',
    CURRENT_DATE,
    NULL
);

INSERT INTO notice_details (notice_summary, notice_details, notic_beg_date, notic_end_date, create_date, update_date)
VALUES (
    'Launch of Millennia Cards',
    'Millennia Credit Cards are launched for the premium customers of EazyBank. With these cards, you will get 5% cashback for each purchase',
    CURRENT_DATE - INTERVAL '30 days',
    CURRENT_DATE + INTERVAL '30 days',
    CURRENT_DATE,
    NULL
);

INSERT INTO notice_details (notice_summary, notice_details, notic_beg_date, notic_end_date, create_date, update_date)
VALUES (
    'COVID-19 Insurance',
    'EazyBank launched an insurance policy which will cover COVID-19 expenses. Please reach out to the branch for more details',
    CURRENT_DATE - INTERVAL '30 days',
    CURRENT_DATE + INTERVAL '30 days',
    CURRENT_DATE,
    NULL
);

CREATE TABLE IF NOT EXISTS contact_messages(
contact_id varchar(50) not null PRIMARY KEY,
contact_name varchar(50) not null,
contact_email varchar(100) not null,
subject varchar(500) not null,
message varchar(2000) not null,
create_date date DEFAULT null
);

insert into authorities(customer_id,name)
values(1, 'VIEWACCOUNT');
insert into authorities(customer_id,name)
values(1, 'VIEWCARDS');
insert into authorities(customer_id,name)
values(1, 'VIEWLOANS');
insert into authorities(customer_id,name)
values(1, 'VIEWBALANCE');





--
--CREATE TABLE IF NOT EXISTS users
--(
--    username VARCHAR(45) PRIMARY KEY,
--    password VARCHAR(45) NOT NULL,
--    enabled  BOOLEAN     NOT NULL
--);
--CREATE TABLE IF NOT EXISTS authorities
--(
--    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--    username  VARCHAR(45) NOT NULL,
--    authority VARCHAR(45) NOT NULL,
--    constraint fk_authorities_users foreign key (username) references users (username)
--);
--INSERT INTO users (username, password, enabled)
--VALUES ('user1', 'password1', true),
--       ('user2', 'password2', true),
--       ('user3', 'password3', true);
--
---- Inserting authorities for the users
--INSERT INTO authorities (username, authority)
--VALUES ('user1', 'ROLE_USER'),
--       ('user2', 'ROLE_ADMIN'),
--       ('user3', 'ROLE_USER');
--
