package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;

public class Item {
   private String id;
   private String name;
   private String[] categories;
   private String currently;
   private String buy_price;
   private String first_bid;
   private String number_of_bids;
   private Bid[] bids;
   private Location location;
   private String country;
   private String started;
   private String ends;
   private User seller;
   private String description;

   public String getID() {return id;}
   public String getName() {return name;}
   public String[] getCategories() {return categories;}
   public String getCurrently() {return currently;}
   public String getBuyPrice() {return buy_price;}
   public String getFirstBid() {return first_bid;}
   public String getNumberOfBids() {return number_of_bids;}
   public Bid[] getBids() {return bids;}
   public Location getLocation() {return location;}
   public String getCountry() {return country;}
   public String getStarted() {return started;}
   public String getEnds() {return ends;}
   public User getSeller() {return seller;}
   public String getDescription() {return description;}

   public void setID(final String _id) {id = _id;}
   public void setName(final String _name) {name = _name;}
   public void setCategories(final String[] _categories) {categories = _categories;}
   public void setCurrently(final String _currently) {currently = _currently;}
   public void setBuyPrice(final String _buy_price) {buy_price = _buy_price.equals("")?null:_buy_price;}
   public void setFirstBid(final String _first_bid) {first_bid = _first_bid;}
   public void setNumberOfBids(final String _number_of_bids) {number_of_bids = _number_of_bids;}
   public void setBids(final Bid[] _bids) {bids = _bids;}
   public void setLocation(final Location _location) {location = _location;}
   public void setCountry(final String _country) {country = _country;}
   public void setStarted(final String _started) {started = _started;}
   public void setEnds(final String _ends) {ends = _ends;}
   public void setSeller(final User _seller) {seller = _seller;}
   public void setDescription(final String _description) {description = _description.equals("")?null:_description;}

   public void orderBids(){
     quickSort(0, bids.length - 1, bids);
   }

   private void quickSort(int begin, int end, Bid _bids[]){
     if (begin >= end) return;
     int left = begin, right = end;
     Bid key = _bids[left];
     while (left < right){
       while ((left < right) && bidCompare(key, _bids[right])) right--;
       _bids[left] = _bids[right];
       while ((left < right) && bidCompare(_bids[left], key)) left++;
       _bids[right] = _bids[left];
     }
     _bids[left] = key;
     quickSort(begin, left - 1, _bids);
     quickSort(left + 1, end, _bids);
   }

   private boolean bidCompare(Bid b1, Bid b2){
     // if b1.time > b2.time || (b1.time = b2.time && b1.amout >= b2.amout) return true
     // true if b1 should rank before b2
     final DateFormat format = new SimpleDateFormat("MMM-dd-yy kk:mm:ss");
     Date date1 = null;
     Date date2 = null;
     try {
        date1 = format.parse(b1.getTime());
        date2 = format.parse(b2.getTime());
     } catch (ParseException e) {
        e.printStackTrace();
        System.exit(0);
     }
     return date1.after(date2) || (date1.equals(date2) && (b1.getMoney() >= b2.getMoney()));
   }

}
