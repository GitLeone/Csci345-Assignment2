import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LocationManager{
   private List<String> locationList;
   private Map<String, String[]> playersInLocation; //Tracks what players are in a specific locaton
   private Map<String, String[]> neighbors; //Tracks what neighbors a specific location has
   
   public LocationManager(List<String> locationList){
       this.locationList = locationList;
       this.playersInLocation = new HashMap<>();
       this.neighbors = new HashMap<>();
   }

   public void updateLocationMap(String player, String location){

   }
   public boolean validateMove(String player, String location){
    //if the location the player wants to move to is not a neighbor of their current location, return false, else return true
    return false;
   }
}
