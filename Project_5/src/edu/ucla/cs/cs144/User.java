package edu.ucla.cs.cs144;

public class User {
   private String id;
   private String rating;
   private String location;
   private String country;

   public User(){
      id = "";
      rating = "";
      location = "";
      country = "";
   }

   public User(final String _id, final String _rating) {
      id = _id;
      rating = _rating;
      location = null;
      country = null;
   }

   public User(final String _id, final String _rating, final String _location, final String _country) {
      id = _id;
      rating = _rating;
      location = _location.equals("")? null:_location;
      country = _country.equals("")? null:_country;
   }

   public String getID() {return id;}
   public String getRating() {return rating;}
   public String getLocation() {return location;}
   public String getCountry() {return country;}
}
