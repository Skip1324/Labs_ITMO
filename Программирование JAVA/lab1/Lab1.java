public class Lab_1 {
    public static void main(String[] args) {
        int[] n = new int[14];
        n[0] = 6;
        for (int i = 0; i < n.length; i++) {
            n[i] = n[0] + i;
            System.out.println(n[i]);
        }  // ПУНКТ №1
        double[] x = new double[20];
        double max = 10;
        double min = -3;
        for (int i = 0; i < x.length; i++) {
            double l = Math.random() * (max - min) + min;
            x[i] = l;
        }// ПУНКТ №2
        double a[][] = new double[14][20];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                switch (n[i]) {
                    case 7:
                        a[i][j] = Math.pow(((1 - Math.pow(Math.cos(x[j]), Math.atan((x[j] + 3.5) / 13) / ((2.0 / 3.0) - Math.pow(x[j], x[j] / Math.PI)))) / 2), (Math.pow(x[j], (x[j] * (x[j] + 0.25))) - 1) / 3);
                        break;
                    case 8:
                    case 12:
                    case 13:
                    case 15:
                    case 16:
                    case 18:
                    case 19:
                        a[i][j] = Math.log(Math.pow(Math.E, Math.asin(Math.cos(x[j]))));
                        break;
                    default:
                        a[i][j] = Math.asin(Math.pow(Math.E, Math.cbrt(-2 * Math.pow(Math.tan(x[j]), 2))));
                        break;

                }
                System.out.printf("%.5f", a[i][j]);
                System.out.print("  ");

            }
            System.out.println(); // ПУНКТ №3 */
        }

    }
}
