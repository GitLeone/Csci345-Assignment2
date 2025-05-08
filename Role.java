public class Role {
    private String name;
    private String line;
    private int rankRequired;
    private boolean isStarring;
    private Player occupiedBy;

    public Role(int rankRequired, boolean isStarring) {
        this.rankRequired = rankRequired;
        this.isStarring = isStarring;
        this.occupiedBy = null;
    }

    public String getName(){
        return this.name;
    }

    public String getLine(){
        return this.line;
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
