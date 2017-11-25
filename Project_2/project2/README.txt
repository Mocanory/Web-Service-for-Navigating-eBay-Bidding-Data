================================================================================
This File is created for CS-144 Project 2.
Team member:
              Yue Xin    (UID: 204775695)  <yuexin@cs.ucla.edu>
              Zhehan Li  (UID: 404888352)  <lizhehan@cs.ucla.edu>
================================================================================

[Caution] The queries in Part E may run for 30s. Please wait.

--------------------------------------------------------------------------------
****************       Part B: Relational Schema Design        *****************
1. List your relations. Please specify all keys that hold on each relation.

Bidder(BidderID, Rating, Location, Country) [BidderID] is the key

Seller(SellerID, Rating) [SellerID] is the key

Bid(ItemID, BidderID, Time, Amount) [ItemID, BidderID, Time] is the key

Item(ItemID, Name, Currently, Buy_Price, First_Bid, Number_of_Bids, Location,
Latitude, Longitude, Country, Started, Ends, SellerID, Description) [ItemID] is
the key

Category(ItemID, Category) [ItemID, Category] is the key

--------------------------------------------------------------------------------
2. List all completely nontrivial functional dependencies that hold on each
relation, excluding those that effectively specify keys.

BidderID -> Rating, Location, Country
SellerID -> Rating
ItemID, BidderID, Time -> Amount
ItemID -> Name, Currently, Buy_Price, First_Bid, Number_of_Bids, Location,
          Latitude, Longitude, Country, Started, Ends, SellerID, Description

--------------------------------------------------------------------------------
3. Are all of your relations in Boyce-Codd Normal Form (BCNF)? If not, either
redesign them and start over, or explain why you feel it is advantageous to use
non-BCNF relations.

Yes, all the relations are in BCNF.

--------------------------------------------------------------------------------
4. Are all of your relations in Fourth Normal Form (4NF)? If not, either
redesign them and start over, or explain why you feel it is advantageous to use
non-4NF relations.

Yes, all the relations are in 4NF.

--------------------------------------------------------------------------------

[Caution] The queries in Part E may run for 30s. Please wait.

================================================================================
This File is created for CS-144 Project 2.
Team member:
              Yue Xin    (UID: 204775695)  <yuexin@cs.ucla.edu>
              Zhehan Li  (UID: 404888352)  <lizhehan@cs.ucla.edu>
================================================================================

