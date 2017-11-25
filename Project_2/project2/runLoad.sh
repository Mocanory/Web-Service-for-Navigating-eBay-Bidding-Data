#!/bin/bash

FILE=("Bidder.csv" "Seller.csv"  "Bid.csv" "Item.csv" "Category.csv")
TEMP="Temp.csv"

# Run the drop.sql batch file to drop existing tables
# Inside the drop.sql, you sould check whether the table exists. Drop them ONLY if they exists.
echo "Dropping tables"
mysql CS144 < drop.sql

# Run the create.sql batch file to create the database and tables
echo "Creating tables"
mysql CS144 < create.sql

# Compile and run the parser to generate the appropriate load files
echo "Parsing files"
ant run-all
ant clean

# If the Java code does not handle duplicate removal, do this now
echo "Cleaning files"
for var in ${FILE[@]};do
  sort $var > temp.csv
  uniq temp.csv > $var
done

# Run the load.sql batch file to load the data
echo "Loading tables"
mysql CS144 < load.sql

# Remove all temporary files
echo "Removing temporary files"
rm *.csv

echo "[Caution : The queries in Part E may run for 30s.]"
