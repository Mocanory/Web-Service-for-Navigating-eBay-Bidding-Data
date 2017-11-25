LOAD DATA LOCAL INFILE './Bidder.csv' INTO TABLE Bidder CHARACTER SET utf8
FIELDS TERMINATED BY "|*$|"
LINES TERMINATED BY '\n'
(BidderID, Rating, @Location, @Country)
SET Location = IF(@Location = '', NULL, @Location),
    Country = IF(@Country = '', NULL, @Country);

LOAD DATA LOCAL INFILE './Seller.csv' INTO TABLE Seller CHARACTER SET utf8
FIELDS TERMINATED BY "|*$|"
LINES TERMINATED BY '\n';

LOAD DATA LOCAL INFILE './Item.csv' INTO TABLE Item CHARACTER SET utf8
FIELDS TERMINATED BY "|*$|"
LINES TERMINATED BY '\n'
(ItemID, Name, Currently, @Buy_Price, First_Bid, Number_of_Bids, Location,
@Latitude, @Longitude, Country, @Started, @Ends, SellerID, Description)
SET Buy_Price = IF(@Buy_Price = '', NULL, @Buy_Price),
    Latitude = IF(@Latitude = '', NULL, @Latitude),
    Longitude = IF(@Longitude = '', NULL, @Longitude),
    Started = STR_TO_DATE(@Started, '%b-%d-%y %H:%i:%s'),
    Ends = STR_TO_DATE(@Ends, '%b-%d-%y %H:%i:%s');

LOAD DATA LOCAL INFILE './Bid.csv' INTO TABLE Bid CHARACTER SET utf8
FIELDS TERMINATED BY "|*$|"
LINES TERMINATED BY '\n'
(ItemID, BidderID, @Time, Amount)
SET Time = STR_TO_DATE(@Time, '%b-%d-%y %H:%i:%s');

LOAD DATA LOCAL INFILE './Category.csv' INTO TABLE Category CHARACTER SET utf8
FIELDS TERMINATED BY "|*$|"
LINES TERMINATED BY '\n';
