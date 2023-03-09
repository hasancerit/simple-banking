SET GLOBAL log_bin_trust_function_creators = 1;

CREATE TABLESPACE SIMPLE_BANKING ADD DATAFILE 'simplebanking.ibd' Engine=InnoDB;
CREATE TABLESPACE SIMPLE_BANKING_TBS ADD DATAFILE 'simplebanking_tbs.ibd' Engine=InnoDB;

CREATE DATABASE IF NOT EXISTS simplebanking;

CREATE USER IF NOT EXISTS simplebanking IDENTIFIED BY 'simplebankingpass';
GRANT ALL PRIVILEGES ON simplebanking.* TO 'simplebanking';
FLUSH PRIVILEGES;

COMMIT;