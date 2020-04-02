package generator.structure;

import java.io.*;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws IOException {
        final int SIZE = StructureConstants.SIZE;
        final double POROSITY = StructureConstants.POROSITY;

        StructureGenerator structure = new StructureGenerator(POROSITY, SIZE);
        BufferedWriter w = null;
        String str = String.format(Locale.ROOT, "structure_%.3f.txt", POROSITY);

        try {
            w = new BufferedWriter(new FileWriter(str));
            structure.writeToTextFile(w);
        } finally {
            if (w != null) {
                w.close();
            }
        }
    }
}
