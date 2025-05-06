import java.util.Random;

public class Dice {
    private final Random random;
    private final int sides;

    public Dice() {
        this(6);
    }

    public Dice(int sides) {
        this.sides = sides;
        long seed = System.nanoTime(); // Seed using current time
        this.random = new Random(seed);
    }

    public int roll() {
        return random.nextInt(sides) + 1;
    }

    public int getSides() {
        return sides;
    }

}
