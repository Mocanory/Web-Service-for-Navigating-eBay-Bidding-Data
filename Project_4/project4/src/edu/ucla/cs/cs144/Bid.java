package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;

public class Bid {
   private User bidder;
   private String time;
   private String amount;

   public Bid(final User _bidder, final String _time, final String _amount) {
      bidder = _bidder;
      time = _time;
      amount = _amount;
   }

   public User getBidder() {return bidder;}
   public String getTime() {return time;}
   public String getAmount() {return amount;}
   public double getMoney(){
       if (amount.equals("")) return 0.0;
       double am = 0.0;
       NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
       try { am = nf.parse(amount).doubleValue(); }
       catch (ParseException e) {
         System.out.println("This method should work for all " +
                            "money values you find in our data.");
         System.exit(20);
       }
       return am;
   }
}
