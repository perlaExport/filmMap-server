package pl.perlaexport.filmmap.als;

public class Gauss{
    public static double[] partialGauss(double[][] A, double[] B){
        int n = A.length;
        for (int p = 0; p < n; p++) {

            // finding max in A
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (A[i][p] > A[max][p]) {
                    max = i;
                }
            }
            // swap lines
            if(max!=p) {
                double[] temp = A[p];
                A[p] = A[max];
                A[max] = temp;
                double t = B[p];
                B[p] = B[max];
                B[max] = t;
            }

            //reset
            for (int i = p + 1; i < n; i++) {
                double alpha = (A[i][p] / A[p][p]);
                B[i] -= (alpha * B[p]);
                for (int j = p; j < n; j++) {
                    A[i][j] -= (alpha * A[p][j]);
                }
            }
        }
        double[] x=backSubsti(A,B);
        return x;
    }
    public static double[] backSubsti(double[][] A,double[] B){
        double [] x = new double[A.length];
        for (int i = A.length - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < A.length; j++) {
                sum += (A[i][j] * x[j]); //sum+A[i][j] * x[j];
            }
            double sub = B[i] - sum;
            double div = sub / A[i][i];
            x[i] =  div; //(B[i] - sum) / A[i][i];
        }
        return x;
    }
}
