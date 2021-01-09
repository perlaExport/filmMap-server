package pl.perlaexport.filmmap.als;
import java.util.ArrayList;
import java.util.List;

public class ALSUtilities {

    public static final int D = 10;

    public static final double Lambda = 0.1;

    public static int[] getIndex(int[] column) {
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < column.length; i++) {
            if (column[i] != 0) {
                temp.add(i);
            }
        }
        return temp.stream().mapToInt(i -> i).toArray();
    }

    public static int[] getColumnInt(int [][] input, int index) {
        int[] result = new int[input.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = input[i][index];
        }
        return result;
    }

    public static double[] getColumnDouble(double [][] input, int index) {
        double[] result = new double[input.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = input[i][index];
        }
        return result;
    }

    public static void setColumn(double[][] input, double[] column, int index) {
        for (int i = 0; i < column.length; i++) {
            input[i][index] = column[i];
        }
    }


    public static int[] getRow(int[][] input, int index) {
        int[] result = new int[input[0].length];
        for(int i = 0; i < input[0].length; i++ ) {
            result = input[index];
        }
        return result;
    }

    public static double[][] createFromIndexes(double[][] input, int[] indexes) {
        double[][] result = new double[D][indexes.length];
        for (int i = 0; i <indexes.length; i++){
            setColumn(result,getColumnDouble(input, indexes[i]),i);
        }
        return result;
    }

    public static double[][] transpose(double[][] array) {
        double[][] result = new double[array[0].length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j< array[0].length; j++){
                result[j][i] = array[i][j];
            }
        }
        return result;
    }

    public static double[][] sumMatrixes(double [][] matrix1, double[][] matrix2) {
        double[][] result = new double[matrix1.length][matrix1.length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1.length; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }

    public static double[] sumMatrixesOneDim(double[] matrix1, double[] matrix2) {
        double[] result = new double[matrix1.length];
        for (int i = 0; i < matrix1.length; i++) {
            result[i] = matrix1[i] + matrix2[i];
        }
        return result;
    }

    public static double[][] multiplyMatrixes(double[][] matrix1, double[][] matrix2) {

        double[][] result = new double[matrix1.length][matrix2[0].length];
        for ( int row = 0; row < result.length; row++ ) {
            for ( int col = 0; col < result[row].length; col++ ) {
                result[row][col] = getMultiplicatedMatrixRows(matrix1, matrix2, row, col);
            }
        }
        return result;
    }

    public static double getMultiplicatedMatrixRows(double[][] matrix1, double[][] matrix2, int row, int col) {
        double result = 0;
        for ( int i = 0; i < matrix2.length; i++ ) {
            result += matrix1[row][i] * matrix2[i][col];
        }
        return result;
    }

    public static double multiplyMatrixesOneDim(double[] matrix1, double[] matrix2) {
        double result = 0;
        for (int i = 0; i < matrix2.length; i++) {
            result += matrix1[i] * matrix2[i];
        }
        return result;
    }

    public static double[][] multiplyMatrixesByNumber(double[][] matrix, double number) {
        double[][] result = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i][j] = matrix[i][j] * number;
            }
        }
        return result;
    }

    public static double[] multiplyMatrixesByNumberOneDim(double number, double[] matrix) {
        double[] result = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = matrix[i] * number;
        }
        return result;
    }

    public static double[] getVector(int[] indexes, double[][] matrix, int[] column) {
        double[] result = new double[D];
        for (int index : indexes) {
            result = sumMatrixesOneDim(result, multiplyMatrixesByNumberOneDim(column[index], getColumnDouble(matrix, index)));
        }
        return result;
    }

    public static double[][] getUnitMatrix(int size) {
        double[][] unitMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    unitMatrix[i][j] = 1;
                } else {
                    unitMatrix[i][j] = 0;
                }
            }
        }
        return unitMatrix;
    }

    public static double getNorm(double[] matrix) {
        double result = 0;
        for (double v : matrix) {
            result += Math.pow(v, 2);
        }
        result = Math.sqrt(result);
        return result;
    }

    public static double[][] ALS(double [][] piu, double[][] e, double lambda) {
        if (piu.length > 0 && piu[0].length > 0) {
            double[][] result;
            double[][] temp1 = multiplyMatrixes(piu, transpose(piu));
            double[][] temp2 = multiplyMatrixesByNumber(e, lambda);
            return sumMatrixes(temp1, temp2);
//            return result;
        }
        return new double[0][0];
    }

    public static double goalFunction(double[][] P, double[][] U, int [][] input, double lambda) {
        double result = 0;
        double tempresult = 0;
        for (int i = 0; i < U[0].length; i++) {
            for (int j = 0; j < P[0].length; j++) {
                if (input[i][j] != 0) {
                    result += Math.pow(input[i][j] - multiplyMatrixesOneDim(getColumnDouble(U, i), getColumnDouble(P, j)), 2);
                }
            }
        }

        for (int i = 0; i < U[0].length; i++) {
            tempresult += Math.pow(getNorm(getColumnDouble(U, i)), 2);
        }

        for (int j = 0; j < P[0].length; j++) {
            tempresult += Math.pow(getNorm(getColumnDouble(P, j)), 2);
        }

        result += tempresult * lambda;
        return result;
    }
}