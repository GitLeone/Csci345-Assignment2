public class Role {
    private int rankRequired;
    private boolean isStarring;
    private Player occupiedBy;

    public Role(int rankRequired, boolean isStarring) {
        this.rankRequired = rankRequired;
        this.isStarring = isStarring;
        this.occupiedBy = null;
    }

    public boolean isAvailable() {
        return occupiedBy == null;
    }

    public void assignPlayer(Player player) {
        this.occupiedBy = player;
    }

    public int getRankRequired() {
        return rankRequired;
    }

}
