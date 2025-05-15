import java.util.ArrayList;
import java.util.List;

public class Set {
    private String name;
    private SceneCard sceneCard;
    private List<Role> offRoles;
    private int maxShots;
    private int shotsRemaining;
    private List<Set> adjacentSets;
    private List<Player> players; //Players currently in this location

    public Set(String name, int maxShots) {
        this.name = name;
        this.maxShots = maxShots;
        this.shotsRemaining = maxShots;
        this.offRoles = new ArrayList<>();
        this.adjacentSets = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    //How we manage the scenes
    public void setSceneCard(SceneCard sceneCard) {
        this.sceneCard = sceneCard;
    }

    public SceneCard getSceneCard() {
        return this.sceneCard;
    }

    public boolean hasScene() {
        return this.sceneCard != null;
    }

    //Controls the shots taken and removes when player utilizies shot
    public void removeShot() {
        if (this.shotsRemaining > 0) {
            this.shotsRemaining--;
            System.out.println("Shot removed! Shots remaining: " + this.shotsRemaining);
        }
    }

    public boolean isSceneWrapped() {
        return this.shotsRemaining == 0;
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
    public void addAdjacentSet(Set set) {
        adjacentSets.add(set);
    }

    public List<Set> getAdjacentSets() {
        return this.adjacentSets;
    }

    public int getShotsRemaining() {
        return this.shotsRemaining;
    }

    public void setShotsRemaining(int shots) {
        this.shotsRemaining = shots;
    }

    public String getName() {
        return this.name;
    }

    public void addOffRole(Role offRole){
        offRoles.add(offRole);
    }
}
