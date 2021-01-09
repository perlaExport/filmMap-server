package pl.perlaexport.filmmap.unit.als;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import pl.perlaexport.filmmap.als.ALSUtilities;

import static org.junit.jupiter.api.Assertions.*;

public class ALSUtilitiesTests {
    public static final int[][] matrix = new int[][] {
            {5,5,5,5,5,5,5,5,5,5},
            {4,3,4,5,4,4,4,4,4,4},
            {3,3,3,3,3,3,3,3,3,3},
            {5,5,5,5,5,4,4,5,5,5},
            {5,5,5,4,4,5,5,5,5,5},
            {4,4,4,4,4,5,5,0,5,5},
            {4,4,4,4,4,4,4,4,4,4},
            {0,5,5,5,0,0,0,5,5,5},
            {4,0,0,4,4,4,0,0,4,4},
            {1,1,1,0,0,1,1,1,0,1}
    };

    public static final double[][] matrixD = new double[][] {
            {5,5,5,5,5,5,5,5,5,5},
            {4,3,4,5,4,4,4,4,4,4},
            {3,3,3,3,3,3,3,3,3,3},
            {5,5,5,5,5,4,4,5,5,5},
            {5,5,5,4,4,5,5,5,5,5},
            {4,4,4,4,4,5,5,0,5,5},
            {4,4,4,4,4,4,4,4,4,4},
            {0,5,5,5,0,0,0,5,5,5},
            {4,0,0,4,4,4,0,0,4,4},
            {1,1,1,0,0,1,1,1,0,1}
    };

    private static final int[] column = new int[]{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 };

    @Test
    public void getIndexTest() {
        int[] expected = new int[]{0,2,3,4,5,6,7,8,9};
        int[] actual = ALSUtilities.getIndex(column);

        assertArrayEquals(expected,actual);
    }

    @Test
    public void getColumnIntTest() {
        int[] expected = new int[]{5,4,3,5,5,4,4,0,4,1};
        int[] actual = ALSUtilities.getColumnInt(matrix, 0);

        assertArrayEquals(expected,actual);
    }

    @Test
    public void getColumnDoubleTest() {
        double[] expected = new double[]{5,4,3,5,5,4,4,0,4,1};
        double[] actual = ALSUtilities.getColumnDouble(matrixD, 0);

        assertArrayEquals(expected,actual);
    }

    @Test
    public void getRowTest() {
        int[] expected = new int[]{5,5,5,5,5,5,5,5,5,5};
        int[] actual = ALSUtilities.getRow(matrix, 0);

        assertArrayEquals(expected,actual);
    }

    @Test
    public void createFromIndexesTest() {
        double[][] expected = new double[][] {
                {5,5,5,5,5,5,5,5,5,5},
                {3,4,3,3,3,3,3,3,3,3},
                {3,3,3,3,3,3,3,3,3,3},
                {5,5,5,5,5,5,5,5,5,5},
                {5,5,5,5,5,5,5,5,5,5},
                {4,4,4,4,4,4,4,4,4,4},
                {4,4,4,4,4,4,4,4,4,4},
                {5,0,5,5,5,5,5,5,5,5},
                {0,4,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,1,1}
        };
        double[][] actual = ALSUtilities.createFromIndexes(matrixD, column);

        assertTrue(Arrays.deepEquals(expected,actual));
    }

    @Test
    public void transposeTest() {
        double[][] given = new double[][] {
                {5,1,3},
                {5,1,3},
                {5,1,3}
        };
        double[][] expected = new double[][] {
                {5,5,5},
                {1,1,1},
                {3,3,3}
        };
        double[][] actual = ALSUtilities.transpose(given);

        assertTrue(Arrays.deepEquals(expected,actual));
    }

    @Test
    public void sumMatrixesTest() {
        double[][] given = new double[][] {
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        double[][] expected = new double[][] {
                {2,2,2},
                {2,2,2},
                {2,2,2}
        };

        double[][] actual = ALSUtilities.sumMatrixes(given, given);

        assertTrue(Arrays.deepEquals(expected,actual));
    }

    @Test
    public void sumMatrixesOneDimTest() {
        double[] given = new double[]{1,1,1};
        double[] expected = new double[]{2,2,2};

        double[] actual = ALSUtilities.sumMatrixesOneDim(given, given);

        assertArrayEquals(expected,actual);
    }

    @Test
    public void getMultiplicatedMatrixRowsTest() {
        double[][] given = new double[][] {
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        double[][] expected = new double[][] {
                {2,2,2},
                {2,2,2},
                {2,2,2}
        };

        double actual = ALSUtilities.getMultiplicatedMatrixRows(given,expected,1,1);

        assertEquals(6,actual);
    }

    @Test
    public void multiplyMatrixesTest() {
        double[][] given = new double[][] {
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        double[][] expected = new double[][] {
                {3,3,3},
                {3,3,3},
                {3,3,3}
        };

        double[][] actual = ALSUtilities.multiplyMatrixes(given, given);

        assertTrue(Arrays.deepEquals(expected,actual));
    }

    @Test
    public void multiplyMatrixesOneDimTest() {
        double[] given = new double[]{1,1,1};
        double expected = 3;

        double actual = ALSUtilities.multiplyMatrixesOneDim(given, given);

        assertEquals(expected,actual);
    }

    @Test
    public void multiplyMatrixesByNumberTest() {
        double[][] given = new double[][] {
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };
        double[][] expected = new double[][] {
                {3,3,3},
                {3,3,3},
                {3,3,3}
        };

        double[][] actual = ALSUtilities.multiplyMatrixesByNumber(given, 3);

        assertTrue(Arrays.deepEquals(expected,actual));
    }

    @Test
    public void multiplyMatrixesByNumberOneDimTest() {
        double[] given = new double[]{1,1,1};
        double[] expected = new double[]{3,3,3};

        double[] actual = ALSUtilities.multiplyMatrixesByNumberOneDim(3, given);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void getUnitMatrixTest() {
        double[][] expected = new double[][] {
                {1,0,0},
                {0,1,0},
                {0,0,1}
        };

        double[][] actual = ALSUtilities.getUnitMatrix(3);

        assertTrue(Arrays.deepEquals(expected,actual));
    }

    @Test
    public void getVectorTest() {
        double[] given = new double[]{3,3,3};
        double expected = Math.sqrt(27);

        double actual = ALSUtilities.getNorm(given);

        assertEquals(expected, actual);
    }
}
