//Will have functions from View just like TextView
//This is the GUI implementation of TextView
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GUIView implements View{
    private GUIController guiMod;
    private LocationManager lm;
    private DeadwoodFrame frame;

    public GUIView(GUIController guiMod, LocationManager lm){
        this.lm = lm;
        this.guiMod = guiMod;
        javax.swing.SwingUtilities.invokeLater(() -> {
            new DeadwoodFrame(guiMod); // Shows the GUI frame
        });
    }

    @Override
    public void displayStartingStats(Player player){}

    @Override
    public void displayPlayerInfo(Player player){}

    @Override
    public void displayPlayerLocation(Player player){}
    
    @Override
    public void displayActResult(Player player, boolean success, boolean onCard){}

    @Override
    public void promptPlayerCount(){}

    @Override
    public void startGameMessage(){}

    @Override
    public void endGameMessage(){}

    @Override
    public void promptAction(){}

    @Override
    public void displayHelp(){}

    @Override
    public void displayMessage(String message){}

    @Override
    public Role chooseFromAvailableRoles(Player player) {
        Set location = lm.getSet(player.getLocation());
        Map<String, Role> allRoles = new HashMap<>();
    
        // Show off card roles with 
        System.out.println("\nOff-Card Roles:");
        int index = 1;
        for (Role role : location.getOffRoles().values()) {
            if (role.isAvailable()) {
                System.out.printf("%d. %s (Rank %d)\n", index++, role.getName(), role.getRankRequired());
                allRoles.put(String.valueOf(index-1), role);
            }
        }

    // Show on card roles 
        System.out.println("\nOn-Card Roles:");
        if (location.getSceneCard() != null) {
            for (Role role : location.getSceneCard().getRoleList().values()) {
                if (role.isAvailable()) {
                    System.out.printf("%d. %s (Rank %d)\n", index++, role.getName(), role.getRankRequired());
                    allRoles.put(String.valueOf(index-1), role);
            }
        }
        }
        System.out.print("Choose a role by number: ");
        String choice = "test";
        return allRoles.get(choice);
    }

    @Override
    public void displayCurrentPlayer(Player player){}

    @Override
    public void displayNeighbors(Set set){}
}