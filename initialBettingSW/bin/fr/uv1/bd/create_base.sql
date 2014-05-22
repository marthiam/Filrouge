
-- +------------+ 1..1        0..* +---------------+
-- | Subscriber |------------------|      Bet      |
-- +------------+                  +---------------+
-- | firstname  |                  | numberOfToken |
-- | lastname   |                  +---------------+
-- +------------+

-- +--------------+                +---------------------+
-- | subscribers  |                |        bets         |
-- +--------------|                +---------------------+
-- | id        PK <--------+       | id               PK |
-- | firstname    |        |       | number_of_tokens    |
-- | lastname     |        +-------- id_subscriber    FK |
-- +--------------+                +---------------------+



-- Drop tables

drop table if exists bets;
drop table if exists subscribers;

-- Create tables

create table subscribers
(
  id        serial      primary key,
  firstname varchar(15) not null,
  lastname  varchar(15) not null
);

create table bets
(
  id               serial  primary key,
  number_of_tokens integer not null,
  id_subscriber    integer not null references subscribers
);

-- Sample data

insert into subscribers values (nextval('subscribers_id_seq'),'Bernard','Prou'); 		--1
insert into subscribers values (nextval('subscribers_id_seq'),'Andre','Dupnt');			--2
insert into subscribers values (nextval('subscribers_id_seq'),'Lucas','Martinez');		--3
insert into subscribers values (nextval('subscribers_id_seq'),'Delphine','Mayet');		--4
