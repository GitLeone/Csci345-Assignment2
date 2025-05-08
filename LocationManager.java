import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationManager{
   private List<String> locationList = Arrays.asList("main street", "trailers", "saloon", "casting office", "ranch", "secret hideout", "bank", "church", "hotel", "jail", "train station", "general store");
   private Map<Player, String> playerLocations; //Tracks where players are
   private Map<String, List<String>> neighbors; //Tracks what neighbors a specific location has
   
   public LocationManager(Map<Player, String> playerLocations, Map<String, String[]> neighbors){
       this.playerLocations = new HashMap<>();
       this.neighbors = new HashMap<>();
   }
   public void initializeNeighbors(){
        List<String> mainStreetNeighbors = Arrays.asList("trailers", "saloon", "jail");
        List<String> trailersNeighbors = Arrays.asList("main street", "saloon", "hotel");
        List<String> saloonNeighbors = Arrays.asList("main street", "trailers", "bank", "general store");
        List<String> bankNeighbors = Arrays.asList("hotel", "church", "saloon", "ranch");        
        List<String> castingOfficeNeighbors = Arrays.asList("train station", "ranch", "secret hideout");
        List<String> ranchNeighbors = Arrays.asList("general store", "casting office", "secret hideout");
        List<String> secretHideoutNeighbors = Arrays.asList("casting office", "ranch", "church");
        List<String> churchNeighbors = Arrays.asList("secret hideout", "bank", "hotel");
        List<String> trainStationNeighbors = Arrays.asList("casting office", "general store", "jail");
        List<String> jailNeighbors = Arrays.asList("train station", "general store", "main street");
        List<String> generalStoreNeighbors = Arrays.asList("jail", "train station", "ranch");
        List<String> hotelNeighbors = Arrays.asList("bank", "church", "trailers");
        neighbors.put("main street", mainStreetNeighbors);
        neighbors.put("trailers", trailersNeighbors);
        neighbors.put("saloon", saloonNeighbors);
        neighbors.put("bank", bankNeighbors);
        neighbors.put("casting office", castingOfficeNeighbors);
        neighbors.put("ranch", ranchNeighbors);
        neighbors.put("secret hideout", secretHideoutNeighbors);
        neighbors.put("church", churchNeighbors);
        neighbors.put("train station", trainStationNeighbors);
        neighbors.put("jail", jailNeighbors);
        neighbors.put("general store", generalStoreNeighbors);
        neighbors.put("hotel", hotelNeighbors);
   }
   public void updatePlayerLocation(Player player, String location){
        playerLocations.remove(player);
        playerLocations.put(player, location);
   }
   public boolean validateMove(String playerLocation, String location){
    //if the location the player wants to move to is not a neighbor of their current location, return false, else return true
    if(!neighbors.get(playerLocation).contains(location)){
        return false;
    }
    else{
        return true;
    }
   }
   public boolean validateUpgrade(String location){
    if(!locationList.contains(location)){
        return false;
    }
    else{
        return true;
    }
   }
}
