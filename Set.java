import java.util.ArrayList;
import java.util.List;

public class Set {
    private String name;
    private SceneCard sceneCard;
    private List<Role> extraRoles;
    private int maxShots;
    private int shotsRemaining;
    private List<Set> adjacentSets;
    private List<Player> players; //Players currently in this location

    public Set(String name, int maxShots) {
        this.name = name;
        this.maxShots = maxShots;
        this.shotsRemaining = maxShots;
        this.extraRoles = new ArrayList<>();
        this.adjacentSets = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    //How we manage the scenes
    public void setSceneCard(SceneCard sceneCard) {
        this.sceneCard = sceneCard;
    }

    public SceneCard getSceneCard() {
        return sceneCard;
    }

    public boolean hasScene() {
        return sceneCard != null;
    }

    //Controls the shots taken and removes when player utilizies shot
    public void removeShot() {
        if (shotsRemaining > 0) {
            shotsRemaining--;
            System.out.println("Shot removed! Shots remaining: " + shotsRemaining);
        }
    }

    public boolean isSceneWrapped() {
        return shotsRemaining == 0;
    }

    // Player movement
    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    // Adjacency
    public void addAdjacentSet(Set set) {
        adjacentSets.add(set);
    }

    public List<Set> getAdjacentSets() {
        return adjacentSets;
    }

    public int getShotsRemaining() {
        return shotsRemaining;
    }

    public String getName() {
        return name;
    }
}
