
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
            //GameController.endTurn() Something like this
        }
        else{
            setLocation(location);
        }
    }
    public void rehearse(Dice dice, Set set, SceneCard scene){
        if(!getWorking()){
            //player loses turn
        }
        else if(getPracticeChips() == role.getRankRequired()){
            act(dice, set, scene);
        }
        else{
            setPracticeChips(getPracticeChips() + 1);
        }
    }
    public void act(Dice dice, Set set, SceneCard scene){
        //For these parameters, set, scene are probably not needed as the role should know what set and scene its apart of
        //Might only need to
        int roll;
        Role role = getRole();
        if(!getWorking()){
            //player loses turn
        }
        else{
            roll = dice.roll();
            if(roll >= scene.getBudget()){ //succeed
                //Either have player set shotCounter or have the method return true and have the GameController do it
                set.decrementCounter();
                if(role.isStarring()){
                    //gets 2 credits
                }
                else{
                    //gets 1 credit and 1 dollar
                }
            }
            else{ //fail
                if(role.isStarring()){
                    //player loses turn
                }
                else{
                    //player gets 1 dollar
                }
            }
        }
    }
    public void takeRole(){
        
    }
    //Change act to work like this.
    //GameController will call if(!Player.upgrade(rank, currency)){loseTurn(Player)}
    public boolean upgrade(int rank, int currency){
        if(!bank.validateUpgrade(rank, currency)){
            return false;
        }
        else{
            return true;
        }
    }
 }
 