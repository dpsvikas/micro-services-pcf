drop table TEST_ACCOUNT if exists;

create table TEST_ACCOUNT (ID bigint identity primary key, NUMBER varchar(9),
                        NAME varchar(50) not null, BALANCE decimal(8,2), unique(NUMBER));
                        
ALTER TABLE TEST_ACCOUNT ALTER COLUMN BALANCE SET DEFAULT 0.0;
