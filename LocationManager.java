import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LocationManager{
   private List<String> locationList;
   private Map<Player, String> playerLocations; //Tracks where players are
   private Map<String, String[]> neighbors; //Tracks what neighbors a specific location has
   
   public LocationManager(List<String> locationList, Map<Player, String> playerLocations, Map<String, String[]> neighbors){
       this.locationList = locationList;
       this.playerLocations = new HashMap<>();
       this.neighbors = new HashMap<>();
   }

   public void updateLocationMap(Player player, String location){

   }
   public boolean validateMove(String playerLocation, String location){
    //if the location the player wants to move to is not a neighbor of their current location, return false, else return true
    return false;
   }
   public boolean validateUpgrade(String location){
    //if the location the player is in does not match "Casting Office", return false, else return true
    return false;
   }
}
