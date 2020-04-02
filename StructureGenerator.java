package generator.structure;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class StructureGenerator {
    private static final double INERTIA = StructureConstants.INERTIA;
    private static final double INERTIA_SECONDARY = StructureConstants.INERTIA_SECONDARY;
    private double porosity;
    private int[][][] structure;
    final private int n;


    public StructureGenerator (double porosity, int n) throws IllegalArgumentException{
        if (porosity > 1 || porosity < 0) {
            throw new IllegalArgumentException("Porosity should be between 0 and 1");
        }

        if (n < 0) {
            throw new IllegalArgumentException("Size should be a positive number.");
        }

        this.porosity = porosity;
        this.n = n;
        structure = new int[n][n][n];
        int center = (int)Math.floor(n / 2);

        int numberOfParticlesNeeded = (int)(Math.pow(n, 3) * (1 - porosity));
        int numberOfParticles = 0;
        Random rnd = new Random();
        ArrayList<int[]> particles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = -1; j < 2; j += 2) {
                Coordinate coordinate = new Coordinate(center, center, center, INERTIA);
                coordinate.setGoal(i == 0 ? j : 0, i == 1 ? j : 0, i == 2 ? j : 0);
                while (coordinate.isInside(n)) {
                    if (structure[coordinate.getX()][coordinate.getY()][coordinate.getZ()] == 0) {
                        numberOfParticles++;
                        particles.add(new int[]{coordinate.getX(), coordinate.getY(), coordinate.getZ()});
                    }
                    structure[coordinate.getX()][coordinate.getY()][coordinate.getZ()] = 1;

                    coordinate.move();
                }
            }
        }

        while (numberOfParticles < numberOfParticlesNeeded) {
            int i = rnd.nextInt(numberOfParticles);
            Coordinate coordinate = new Coordinate(particles.get(i)[0], particles.get(i)[1], particles.get(i)[2], INERTIA_SECONDARY);
            while (coordinate.isInside(n)) {
                coordinate.move();
                if (coordinate.isInside(n) && structure[coordinate.getX()][coordinate.getY()][coordinate.getZ()] == 0) {
                    numberOfParticles++;
                    structure[coordinate.getX()][coordinate.getY()][coordinate.getZ()] = 1;
                    particles.add(new int[]{coordinate.getX(), coordinate.getY(), coordinate.getZ()});
                } else {
                    break;
                }
            }
        }
    }

    public void writeToTextFile (Writer w) throws IOException {

        String str = String.format(Locale.ROOT, "structure_%.3f.txt%n", porosity);

        w.write(str);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (structure[i][j][k] == 1) {
                        str = String.format("%s\t%s\t%s%n", i, j, k);
                        w.write(str);
                    }
                }
            }
        }
        w.flush();
    }
}
