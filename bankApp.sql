CREATE DATABASE ing;
\c ing;
CREATE TABLE account_info ( id int NOT NULL, ACC_NUMBER varchar(10) UNIQUE NOT NULL,username varchar(50),TRAN_AMOUNT DECIMAL );
CREATE SEQUENCE bank_seq START 1;
CREATE TABLE acc_id_generator ( id int NOT NULL, PARAM_KEY varchar(10),ACC_ID int UNIQUE NOT NULL);
CREATE SEQUENCE bank_seq_generator START 1;
INSERT INTO acc_id_generator VALUES(nextval('bank_seq_generator'),'bank_acc',1);
CREATE unique INDEX ing_index ON account_info (acc_number);