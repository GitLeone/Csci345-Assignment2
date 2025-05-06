public class Player {
    private String name;
    private int rank;
    private int credits;
    private int dollars;
    private int practiceChips;
    private boolean working;
    private String location;

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getRank(){
        return this.rank;
    }
    public void setRank(int rank){}

    public int getCredits(){
        return this.credits;
    }
    public void setCredits(int credits){}

    public int getDollars(){
        return this.dollars;
    }
    public void setDollars(int dollars){}

    public int getPracticeChips(){
        return this.practiceChips;
    }
    public void setPracticeChips(int practiceChips){}
    
    public boolean getWorking(){
        return this.working;
    }
    public void setWorking(){}

    public void move(String location){}
    public void rehearse(){}
    public void takeRole(){}
    public void upgrade(int rank){}
    public void act(){}
}