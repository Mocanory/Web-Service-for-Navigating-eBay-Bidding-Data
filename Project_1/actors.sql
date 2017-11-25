-- select database
USE TEST;
-- check if table already existed
DROP TABLE IF EXISTS Actors;

-- create table Actors
CREATE TABLE Actors(
  Name VARCHAR(40),
  Movie VARCHAR(80),
  Year INTEGER,
  Role VARCHAR(40)
);

-- load data in actors.csv into Actors table
LOAD DATA LOCAL INFILE '~/data/actors.csv' INTO TABLE Actors CHARACTER SET utf8
FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
ESCAPED BY '"'
LINES TERMINATED BY '\n';

-- run the requested query
SELECT Name FROM Actors WHERE Movie = 'Die Another Day';

-- drop Actors table
DROP TABLE Actors;
