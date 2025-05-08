public class Player {
    private String name;
    private int rank;
    private int credits;
    private int dollars;
    private int practiceChips;
    private boolean working;
    private String location;
    private Role role;
    //Moderator creates the location Manager and uses it to make calls like player move?
 
    //constructor
    public Player(String name, int rank, int credits, int dollars, int practiceChips, boolean working, String location, Role role){
        this.name = name;
        this.rank = rank;
        this.credits = credits;
        this.dollars = dollars;
        this.practiceChips = practiceChips;
        this.working = working;
        this.location = location;
        this.role = role;
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
    public void move(String location, LocationManager locationManager){
        if(!locationManager.validateMove(getLocation(), location)){
            //player loses turn
        }
        else{
            setLocation(location);
            //if the players new location is a set, then check if the scenecard in the set has been flipped, if not, flip it
            //Location Managers location maps must also be updated
        }
    }
    public void rehearse(){
        if(!getWorking()){
            //player loses turn
        }
        /*In the GameController case:
            If player.getPracticeChips() == player.getRole().getRankRequired(){
                player.act(dice, set, scene)
            }*/
        else{
            setPracticeChips(getPracticeChips() + 1);
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
            if(roll >= scene.getBudget()){ //succeed
                //Either have player set shotCounter or have the method return true and have the GameController do it
                set.decrementCounter();
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
    public void takeRole(Role role){  
        if(!role.isAvailable()){
            //player loses turn
        }
        else{
            if(getRank() < role.getRankRequired()){
                //player loses turn
            }
            else{
                setRole(role);
                setWorking(true);
                role.assignPlayer(this);
            }
        }
    }

    public void upgrade(int rank, int currency, String currencyType, Bank bank, LocationManager lm){
        if(!bank.validateUpgrade(rank, currency, currencyType) || !lm.validateUpgrade(getLocation())){
            //end turn
        }
        else{
            if(currencyType.equals("Dollars")){
                setDollars(currency - bank.getRankDollarCost(rank));
            }
            else{
                setCredits(currency - bank.getRankCreditCost(rank));
            }  
            setRank(rank);
        }
    }
 }
 