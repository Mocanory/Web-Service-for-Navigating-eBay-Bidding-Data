
package edu.ucla.cs.cs144;

public class Location {
   private String name;
   private String latitude;
   private String longitude;

   public Location(){
      name = "";
      latitude = "";
      longitude = "";
   }

   public Location(final String _name, final String _latitude, final String _longitude) {
      name = _name;
      latitude = _latitude.equals("")?null:_latitude;
      longitude = _longitude.equals("")?null:_longitude;
   }

   public String getName() {return name;}
   public String getLatitude() {return latitude;}
   public String getLongitude() {return longitude;}
}
