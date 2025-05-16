import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class SceneCard{
    private String name;
    private int budget;
    private boolean flipped;
    private int sceneNumber;
    private String scene;
    private Map<String, Role> roleList;
   
    public SceneCard(String name, int budget, boolean flipped){
        this.name = name;
        this.budget = budget;
        this.flipped = flipped;
        this.roleList = new HashMap<>();
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
    public void addPart(Role role){
        roleList.put(role.getName(), role);
    }
    public Role getRole(String role){
        return getRoleList().get(role);
    }
    public Map<String, Role> getRoleList(){
        return this.roleList;
    }
    public List<String> getRoleNameList(){
        List<String> roleNameList = new ArrayList<String>();
        for (String key : getRoleList().keySet()) {
            roleNameList.add(key);
        }
        return roleNameList;
    }
 }