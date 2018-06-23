import java.util.Random;

class FormulasForHelp {


    public static double probabilityVariation(double currentDistance, double newDistance, double temperature) {
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((currentDistance - newDistance) / temperature);
    }


    static double randomDouble()
    {
        Random r = new Random();
        return r.nextInt(1000) / 1000.0;  // to return number in range of [0,1];
    }

  //for swapping cities
    public static int randomInt(int min , int max) {
        Random r = new Random();
        double d = min + r.nextDouble() * (max - min);
        return (int)d;
    }
}
