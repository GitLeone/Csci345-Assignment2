public class Deadwood{
    public static void main(String[] args){
        Dice die = new Dice();

        System.out.println("Hello and welcome to Deadwood!");
        System.out.println("Would you like to hear the rules or jump into a new game? (y/n)");


        System.out.println("Rolling the die...");
        System.out.println("You rolled: " + die.roll());
    }
}
