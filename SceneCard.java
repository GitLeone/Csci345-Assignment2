public class SceneCard{
    private String name;
    private int budget;
    private boolean flipped;
   
    public SceneCard(String name, int budget, boolean flipped){
        this.name = name;
        this.budget = budget;
        this.flipped = flipped;
    }
    
    //Gets the name of scene
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getBudget(){
        return this.budget;
    }
    public void setBudget(int budget){
        this.budget = budget;
    }
    public boolean getFlipped(){
        return this.flipped;
    }
    public void setFlipped(boolean flipped){
        this.flipped = flipped;
    }
    public void wrapScene(){

    }
 }
 