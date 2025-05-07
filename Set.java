import java.util.List;

public class Set{
    private String name;
    private List<String> roleList;
    private int shotCounter;
    private int totalShots;
    private boolean wrapped;

    public Set(String name, List<String> roleList, int shotCounter, int totalShots, boolean wrapped){
        this.name = name;
        this.roleList = roleList;
        this.shotCounter = shotCounter;
        this.totalShots = totalShots;
        this.wrapped = wrapped;
    }
    //getters and setters
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public List<String> getRoleList(){
        return this.roleList;
    }
    public void setRoleList(List<String> roleList){
        this.roleList = roleList;
    }
    public int getShotCounter(){
        return this.shotCounter;
    }
    public void setShotCounter(int shotCounter){
        this.shotCounter = shotCounter;
    }
    public int getTotalShots(){
        return this.totalShots;
    }
    public void setTotalShots(int totalShots){
        this.totalShots = totalShots;
    }
    public boolean getWrapped(){
        return this.wrapped;
    }
    public void setWrapped(boolean wrapped){
        this.wrapped = wrapped;
    }
    
    //methods
    public void decrementCounter(){
        setShotCounter(getShotCounter() - 1);
    }
}