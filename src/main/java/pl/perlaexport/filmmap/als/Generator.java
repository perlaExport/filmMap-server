package pl.perlaexport.filmmap.als;

import java.util.concurrent.ThreadLocalRandom;

public class Generator {

    private static final int MIN = 0;
    private static final int MAX = 1;

    public static double[][] generateRandomMatrix(int x, int y) {
        double[][] result = new double[x][y];
        for (int i = 0; i < x; i++){
            for (int j = 0; j < y; j++) {
                result[i][j] = ThreadLocalRandom.current().nextDouble(MIN, MAX);
            }
        }
        return result;
    }

    public static int[] generateRandomVector(int x) {
        int[] result = new int[x];
        for (int i = 0; i < x; i++){
            result[i] = ThreadLocalRandom.current().nextInt(MIN, MAX);
        }
        return result;
    }
}
