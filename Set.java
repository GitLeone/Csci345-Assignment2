import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Set {
    private String name;
    private boolean isSet;
    private SceneCard sceneCard;
    private Map<String, Role> offRoles;
    private int maxShots;
    private int shotsRemaining;
    private List<String> adjacentSets; //This instance of sets are strings because when creating them through the parse, the Set objects of adjacent sets may not be created yet
                                        //If the Set object from this list is needed, use locationManager.getSet()
    private List<Player> players; //Players currently in this location
    private List<Player> actingPlayers;
    private List<Take> takes;
    private int x, y, h, w;
    private List<Point> basePoints;

    public Set(String name, int maxShots, boolean isSet) {
        this.name = name;
        this.maxShots = maxShots;
        this.shotsRemaining = maxShots;
        this.offRoles = new HashMap<>();
        this.adjacentSets = new ArrayList<>();
        this.players = new ArrayList<>();
        this.isSet = isSet;
        this.actingPlayers = new ArrayList<>();
        this.takes = new ArrayList<>();
        this.basePoints = new ArrayList<>();
    }

    //How we manage the scenes
    public void setSceneCard(SceneCard sceneCard) {
        this.sceneCard = sceneCard;
    }

    public SceneCard getSceneCard() {
        return this.sceneCard;
    }

    public boolean hasSceneCard(){
        return this.sceneCard != null;
    }
    //For seperating office and trailer from sets
    public boolean isSet(){
        return this.isSet;
    }
    //Controls the shots taken and removes when player utilizies shot
    public void removeShot() {
        if (this.shotsRemaining > 0) {
            this.shotsRemaining--;
        }
    }

    // Player movement
    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    // Adjacency
    public void addAdjacentSet(String set) {
        adjacentSets.add(set);
    }

    public List<String> getAdjacentSets() {
        return this.adjacentSets;
    }

    public int getShotsRemaining() {
        return this.shotsRemaining;
    }

    public void setShotsRemaining(int shots) {
        this.shotsRemaining = shots;
    }
    
    public List<Player> getActingPlayers(){
        return this.actingPlayers;
    }

    public void addActingPlayer(Player player){
        actingPlayers.add(player);
    }

    public String getName() {
        return this.name;
    }
    
    public void addOffRole(Role offRole){
        offRoles.put(offRole.getName(), offRole);
    }
    public Map<String, Role> getOffRoles(){
        return this.offRoles;
    }
    public Role getOffRole(String role){
        return getOffRoles().get(role);
    }
    public List<String> getRoleNameList(){
        List<String> roleNameList = new ArrayList<String>();
        for (String key : getOffRoles().keySet()) {
            roleNameList.add(key);
        }
        return roleNameList;
    }

    public List<Take> getTakes(){
        return this.takes;
    }

    public void addTake(Take take){
        this.takes.add(take);
    }

    public int getMaxShots(){
        return this.maxShots;
    }
    
    public int getXCord(){
        return this.x;
    }

    public int getYCord(){
        return this.y;
    }

    public int getHeight(){
        return this.h;
    }

    public int getWidth(){
        return this.w;
    }

    public void setBoardDetails(int x, int y, int h, int w){
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }
}
