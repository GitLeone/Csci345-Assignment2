public class Player {
    private String name;
    private int rank;
    private int credits;
    private int dollars;
    private int practiceChips;
    private boolean working;
    private String location;
    private Role role;

    //constructor
    public Player(String name, int rank, int credits, int dollars, int practiceChips, boolean working, String location){
        this.name = name;
        this.rank = rank;
        this.credits = credits;
        this.dollars = dollars;
        this.practiceChips = practiceChips;
        this.working = working;
        this.location = location;
    }

    //getters & setters
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getRank(){
        return this.rank;
    }
    public void setRank(int rank){
        this.rank = rank;
    }
    public int getCredits(){
        return this.credits;
    }
    public void setCredits(int credits){
        this.credits = credits;
    }
    public int getDollars(){
        return this.dollars;
    }
    public void setDollars(int dollars){
        this.dollars = dollars;
    }
    public int getPracticeChips(){
        return this.practiceChips;
    }
    public void setPracticeChips(int practiceChips){
        this.practiceChips = practiceChips;
    }
    public boolean getWorking(){
        return this.working;
    }
    public void setWorking(boolean working){
        this.working = working;
    }
    public String getLocation(){
        return this.location;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public Role getRole(){
        return this.role;
    }
    public void setRole(Role role){
        this.role = role;
    }
 
    //actions
    public boolean move(String location, LocationManager locationManager) {
    String actualLocationName = null;
    for (String validLocation : locationManager.getSetList().keySet()) {
        if (validLocation.equalsIgnoreCase(location)) {
            actualLocationName = validLocation;
            break;
        }
    }
    
    // Fail if location doesn't exist
    if (actualLocationName == null) {
        System.err.println("Invalid location: " + location);
        return false;
    }

    // Validate move with properly-cased name
    if (!locationManager.validateMove(this, actualLocationName)) {
        return false;
    }

    setLocation(actualLocationName);
    Set targetSet = locationManager.getSet(actualLocationName);
    
    if (targetSet.isSet() && targetSet.getSceneCard() != null) {
        targetSet.getSceneCard().setFlipped(true);
    }
    
    return true;
}

    public boolean rehearse(Set set, SceneCard scene, Dice dice) {
        if (!getWorking()) {
            return false;
        }
        // If the number of practice chips equals the role's required rank, player acts automatically
        if (getPracticeChips() == getRole().getRankRequired()) {
            act(dice, set, scene);
            return true;
        } else {
            setPracticeChips(getPracticeChips() + 1);
            return true;
        }
    }       
    public boolean act(Dice dice, Set set, SceneCard scene){
        if(!getWorking()){
            return false;
        }
        else{
            int roll = dice.roll();
            if(roll + getPracticeChips() >= scene.getBudget()){ //succeed
                //Either have player set shotCounter or have the method return true and have the GameController do it
                set.removeShot();
                setPracticeChips(0);
                if(getRole().getStarring()){
                    //gets 2 credits
                    setCredits(getCredits() + 2);
                }
                else{
                    //gets 1 credit and 1 dollar
                    setCredits(getCredits() + 1);
                    setDollars(getDollars() + 1);
                }
            }
            else{ //fail
                if(getRole().getStarring()){
                    return false;
                }
                else{
                    //player gets 1 dollar
                    setDollars(getDollars() + 1);
                    return false;
                }
            }
        }
        return true;
    }
    //Maybe instead of returning boolean, it returns an int that represents the fail case
    public boolean takeRole(Role role, Set set){  
        if(role.isAvailable()){
            if(getRank() < role.getRankRequired()){
                return false;
            }
            else{
                setRole(role);
                setWorking(true);
                role.assignPlayer(this);
                if (role.getStarring()) {
                    set.getSceneCard().addActingPlayer(this);
                } else {
                    set.addActingPlayer(this);
                }        
                return true;
            }
        }
        else{
            return false;
        }
    }

    public boolean upgrade(int newRank, String currency, Bank bank, LocationManager lm) {
        // Make sure the location is appropriate
        if (!lm.validateUpgrade(this)) {
            System.out.println("You must be in the Casting Office!");
            return false;
        }

        // Verify that new rank is higher than old rank
        if (newRank <= this.rank) {
            System.out.println("New rank must be higher than current rank " + this.rank);
            return false;
        }

        if (currency.equalsIgnoreCase("dollar")) {
            int cost = bank.getRankDollarCost(newRank);
            if (this.dollars < cost) {
                System.out.println("Need $" + cost + " (have $" + this.dollars + ")");
                return false;
            }
            this.dollars -= cost;
            } else {
            int cost = bank.getRankCreditCost(newRank);
            if (this.credits < cost) {
                System.out.println("Need " + cost + " credits (have " + this.credits + ")");
                return false;
            }
            this.credits -= cost;
        }

        this.rank = newRank;
        System.out.println("Upgraded to rank " + newRank + "!");
        return true;
    }
}