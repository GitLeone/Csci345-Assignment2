import java.util.ArrayList;
import java.util.List;

public class Role {
    private String name;
    private String line;
    private int rankRequired;
    private boolean isStarring;
    private Player occupiedBy;

    public Role(String name, boolean isStarring, int rankRequired) {
        this.name = name;
        this.isStarring = isStarring;
        this.rankRequired = rankRequired;
    }

    public String getName(){
        return this.name;
    }

    public String getLine(){
        return this.line;
    }

    public void setLine(String line){
        this.line = line;
    }
    
    public boolean getStarring() {
        return this.isStarring;
    }

    public void setStarring(boolean isStarring) {
        this.isStarring = isStarring;
    }

    public boolean isAvailable() {
        return this.occupiedBy == null;
    }

    public void assignPlayer(Player player) {
        this.occupiedBy = player;
    }

    public int getRankRequired() {
        return this.rankRequired;
    }
}
