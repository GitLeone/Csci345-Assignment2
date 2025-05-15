import java.util.List;
import java.util.ArrayList;

public class SceneCard{
    private String name;
    private int budget;
    private boolean flipped;
    private int sceneNumber;
    private String scene;
    private List<Role> partList;
   
    public SceneCard(String name, int budget, boolean flipped){
        this.name = name;
        this.budget = budget;
        this.flipped = flipped;
        this.partList = new ArrayList<>();
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
    public int getSceneNumber(){
        return this.sceneNumber;
    }
    public void setSceneNumber(int sceneNumber){
        this.sceneNumber = sceneNumber;
    }
    public String getScene(){
        return this.scene;
    }
    public void setScene(String scene){
        this.scene = scene;
    }
    public void addPart(Role part){
        partList.add(part);
    }
 }