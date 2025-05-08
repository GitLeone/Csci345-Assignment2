
public class GameController {
    /*
     * private int currentDay;
     * private int maxDays;
     * private Bank bank;
     * private List<Player> players;
     * private List<Set> sets;
     */

    public static void main(String[] args) {
        Dice die = new Dice();
        System.out.println("Rolling the die...");
        System.out.println("You rolled: " + die.roll());

    }
}
