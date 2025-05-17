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
    public boolean move(String location, LocationManager locationManager){
        if(locationManager.validateMove(this, location)){
            setLocation(location);
            Set locationValue = locationManager.getSet(location);
            //if the players new location is a set, check if the scenecard in the set has been flipped, if not, flip it
            if(locationValue.isSet()){
                if(!locationValue.getSceneCard().getFlipped()){
                    locationValue.getSceneCard().setFlipped(true);
                }
            }
            return true;
        }
        else{
            return false;
        }

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
    public void act(Dice dice, Set set, SceneCard scene){
        //For these parameters, set, scene are probably not needed as the role should know what set and scene its apart of
        //Might only need to
        if(!getWorking()){
            //player loses turn
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
                    //player loses turn
                }
                else{
                    //player gets 1 dollar
                    setDollars(getDollars() + 1);
                }
            }
        }
    }
    //Maybe instead of returning boolean, it returns an int that represents the fail case
    public boolean takeRole(Role role){  
        if(role.isAvailable()){
            if(getRank() < role.getRankRequired()){
                return false;
            }
            else{
                setRole(role);
                setWorking(true);
                role.assignPlayer(this);
                return true;
            }
        }
        else{
            return false;
        }
    }

    public void upgrade(int rank, String currencyType, Bank bank, LocationManager lm){
        if(bank.validateUpgrade(this, rank, currencyType) & lm.validateUpgrade(this)){
            if(currencyType.equals("Dollars")){
                setDollars(getDollars() - bank.getRankDollarCost(rank));
            }
            else{
                setCredits(getCredits() - bank.getRankCreditCost(rank));
            }  
            setRank(rank);
        }
        else{
            //end turn
        }
    }
    public void endTurn(){
        //current player will be set to the next in line
    }
 }
 