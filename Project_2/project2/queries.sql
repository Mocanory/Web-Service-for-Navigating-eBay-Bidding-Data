USE CS144;
-- Find the number of users in the database.
SELECT COUNT(*)
FROM (SELECT BidderID FROM Bidder
      UNION
      SELECT SellerID FROM Seller) T;

-- Find the number of items in "New York", (i.e., items whose location is exactly the string "New York"). Pay special attention to case sensitivity. You should match the items in "New York" but not in "new york".
SELECT COUNT(*)
FROM Item
WHERE BINARY Location = "New York";

-- Find the number of auctions belonging to exactly four categories.
SELECT COUNT(*)
FROM (SELECT ItemID FROM Category
      GROUP BY ItemID
      HAVING COUNT(Category) = 4) T;

-- Find the ID(s) of current (unsold) auction(s) with the highest bid. Remember that the data was captured at the point in time December 20th, 2001, one second after midnight, so you can use this time point to decide which auction(s) are current. Pay special attention to the current auctions without any bid.
SELECT ItemID
FROM Item
WHERE Currently IN (SELECT MAX(Currently)
                    FROM Item
                    WHERE Ends > "2001-12-20 00:00:01"
                          AND Number_of_Bids > 0)
AND Ends > "2001-12-20 00:00:01"
AND Number_of_Bids > 0;

-- Find the number of sellers whose rating is higher than 1000.
SELECT COUNT(*)
FROM Seller
WHERE Rating > 1000;

-- Find the number of users who are both sellers and bidders.
SELECT COUNT(*)
FROM Bidder
WHERE BidderID IN (SELECT SellerID
                   FROM Seller);

-- Find the number of categories that include at least one item with a bid of more than $100.
SELECT COUNT(DISTINCT Category)
FROM Category
WHERE ItemID IN (SELECT ItemID
                 FROM Bid
                 WHERE Amount > 100);
