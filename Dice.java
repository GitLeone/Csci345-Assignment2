import java.util.Random;

public class Dice {
    private final Random random;
    private final int sides;

    public Dice() {
        this(6);
    }

    public Dice(int sides) {
        this.sides = sides;
        long seed = System.nanoTime(); // Seed using current time to make a random for us
        this.random = new Random(seed);
    }

    public int roll() {
        return random.nextInt(sides) + 1;
    }

    public int getSides() {
        return this.sides;
    }
}
