package pl.perlaexport.filmmap.als;

public class ALS {
    public static double[][] getALS(int[][] R) {
        double[][] P = Generator.generateRandomMatrix(ALSUtilities.D, R[0].length);
        double[][] U = Generator.generateRandomMatrix(ALSUtilities.D, R.length);
        int[] Iu;
        int[] Ip;
        double[][] piu, uip, E, Au;
        double[] Vu, Wp, gauss;
        for (int i = 0; i <= 50; i++) {
            for (int u = 0; u < R.length; u++) {
                Iu = ALSUtilities.getIndex(ALSUtilities.getRow(R, u));
                uip = ALSUtilities.createFromIndexes(P, Iu);
                Vu = ALSUtilities.getVector(Iu, P, ALSUtilities.getRow(R, u));
                E = ALSUtilities.getUnitMatrix(ALSUtilities.D);
                Au = ALSUtilities.ALS(uip, E, ALSUtilities.Lambda);
                gauss = Gauss.partialGauss(Au, Vu);
                ALSUtilities.setColumn(U, gauss, u);
            }

            for (int p = 0; p < R[0].length; p++) {
                Ip = ALSUtilities.getIndex(ALSUtilities.getColumnInt(R, p));
                piu = ALSUtilities.createFromIndexes(U, Ip);
                Wp = ALSUtilities.getVector(Ip, U, ALSUtilities.getColumnInt(R, p));
                E = ALSUtilities.getUnitMatrix(ALSUtilities.D);
                Au = ALSUtilities.ALS(piu, E, ALSUtilities.Lambda);
                gauss = Gauss.partialGauss(Au, Wp);
                ALSUtilities.setColumn(P, gauss, p);
            }
        }
        return getResult(U,P);
    }
}
