package generator.structure;

import java.util.Arrays;
import java.util.Random;

public class Direction {
    private int[] direction;
    private int[] moveTo = new int[] {0, 0, 0};
    private Random rnd = new Random();
    private double inertia;

    public Direction (double inertia) {
        if (inertia < 0 || inertia > 1) {
            throw new IllegalArgumentException("Inertia should be between 0 and 1.");
        }

        direction = new int[] {0, 0, 0};
        direction[rnd.nextInt(3)] = rnd.nextInt(2) == 0
                ? 1
                : -1;
        this.inertia = inertia;
    }

    public void setDirection(int dx, int dy, int dz) {
        if (Math.abs(dx) + Math.abs(dy) + Math.abs(dz) > 1) {
            throw new IllegalArgumentException("Direction should be parallel to coordinate axes");
        }
        this.direction = new int[] {dx, dy, dz};
    }

    public void setMoveTo(int dx, int dy, int dz) {
        this.moveTo = new int[] {dx, dy, dz};
    }

    public int[] getDirection() {
        return direction;
    }

    public void switchDirection() {
        if (!Arrays.equals(this.moveTo, new int[] {0, 0, 0}) && Arrays.equals(this.direction, this.moveTo)) {
            if (rnd.nextInt(100) < (1 - inertia) * 100) {
                return;
            }

        }

        if (rnd.nextInt(100) < inertia * 100)
            return;

        int num1 = rnd.nextInt(2);
        int num2 = rnd.nextInt(2);
        if (direction[0] != 0) {
            direction[0] = 0;
            direction[num1 + 1] = num2 == 0
                    ? 1
                    : -1;
        } else if (direction[1] != 0) {
            direction[1] = 0;
            direction[num1 * 2] = num2 == 0
                    ? 1
                    : -1;
        } else {
            direction[2] = 0;
            direction[num1] = num2 == 0
                    ? 1
                    : -1;
        }
    }
}
