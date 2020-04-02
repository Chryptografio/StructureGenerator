package generator.structure;

public class Coordinate {
    private int[] coordinate;
    private Direction direction;

    public Coordinate(int x, int y, int z, double inertia) {
        coordinate = new int[] {x, y, z};
        direction = new Direction(inertia);
    }

    public void setGoal (int dx, int dy, int dz) {
        direction.setDirection(dx, dy, dz);
        direction.setMoveTo(dx, dy, dz);
    }

    public boolean isInside (int n) {
        for (int i = 0; i < 3; i++) {
            if (coordinate[i] < 0 || coordinate[i] >= n)
                return false;
        }
        return true;
    }

    public void move () {
        direction.switchDirection();
        for (int i = 0; i < 3; i++) {
            coordinate[i] += direction.getDirection()[i];
        }
    }

    public int getX () {
        return coordinate[0];
    }

    public int getY () {
        return coordinate[1];
    }

    public int getZ () {
        return coordinate[2];
    }

}
