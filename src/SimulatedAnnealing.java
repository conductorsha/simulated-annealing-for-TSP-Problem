import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SimulatedAnnealing {

    private static ArrayList<double[]> citiesAndDistancesFromThem = new ArrayList<>();
    private static int numberOfcities;
    private static double temperature = 100;
    private static double distance = 0;
    private static double neighbourDistance = 0;
    private static double bestDistance = 0;
    private static ArrayList<Integer> bestRoute = new ArrayList<>();
    private static ArrayList<Integer> cityIndeces = new ArrayList<>();
    private static ArrayList<Integer> neighbourIndeces = new ArrayList<>();

    public static void main(String[] args) {
        readFile("/Users/conductor/Downloads/Simulated-Annealing-for-TSP/src/tsp_data2.txt");   //WRITE YOUR OWN FILEPATH HERE!!!!!!
        Collections.shuffle(cityIndeces);
        bestDistance = calculateSolutionDistance(citiesAndDistancesFromThem, cityIndeces);
        bestRoute = cityIndeces;
        for (int i : bestRoute) System.out.print(i + "->");
        System.out.print(". Distance: " + bestDistance);
        System.out.println();

        while (temperature >= 0.1) {
            distance += calculateSolutionDistance(citiesAndDistancesFromThem, cityIndeces);
            neighbourIndeces = createNeighbour(cityIndeces);
            neighbourDistance += calculateSolutionDistance(citiesAndDistancesFromThem, neighbourIndeces);
            double deltaDistance = neighbourDistance - distance;
            //change to neighbor
            if (deltaDistance < 0) cityIndeces = neighbourIndeces;
            else {
                double rand = FormulasForHelp.randomDouble();
                double probability = FormulasForHelp.probabilityVariation(distance, neighbourDistance, temperature);
                if (probability > rand) {
                    cityIndeces = neighbourIndeces;
                }
            }
            //compare with the best route
            if (neighbourDistance < bestDistance) {
                bestDistance = neighbourDistance;
                bestRoute = neighbourIndeces;
            }

            //decreaseTemperature
            temperature = temperature * 0.999;
            System.out.println(distance);
            distance = 0;
            neighbourDistance = 0;
        }
        for (int i : bestRoute) System.out.print("->" + i);
        System.out.print(". Distance: " + bestDistance);
    }


    private static ArrayList<Integer> createNeighbour(ArrayList<Integer> cityIndeces) {
        ArrayList<Integer> temporary = new ArrayList<>(cityIndeces);
        int randomCity1 = FormulasForHelp.randomInt(0, cityIndeces.size());
        int randomCity2 = FormulasForHelp.randomInt(0, cityIndeces.size());
        while (randomCity1 == randomCity2) randomCity1 = FormulasForHelp.randomInt(0, cityIndeces.size());
        temporary.set(randomCity1, cityIndeces.get(randomCity2));
        temporary.set(randomCity2, cityIndeces.get(randomCity1));
        return temporary;
    }

    private static double calculateSolutionDistance(ArrayList<double[]> citiesAndDistancesFromThem, ArrayList<Integer> cityIndeces) {
        double distance = 0;
        for (int i = 0; i < cityIndeces.size(); i++) {
            if (i + 1 < cityIndeces.size())
                distance += citiesAndDistancesFromThem.get(cityIndeces.get(i))[cityIndeces.get(i + 1)];
            else distance += citiesAndDistancesFromThem.get(cityIndeces.get(i))[cityIndeces.get(0)];
        }
        return distance;
    }


    private static void readFile(String fileName) {
        // This will be the output - a list of rows, each with 'width' entries:
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            ArrayList<String> kasha = new ArrayList<>();
            String forWork;
            double[] dataToAdd;
            String[] tmpArr;
            numberOfcities = 0;
            while ((forWork = bufferedReader.readLine()) != null) {
                dataToAdd = new double[forWork.split(" ").length];
                tmpArr = forWork.split(" ");
                kasha.add(forWork);
                for (int i = 0; i < dataToAdd.length; i++)
                    dataToAdd[i] = Double.parseDouble(tmpArr[i]);
                citiesAndDistancesFromThem.add(dataToAdd);
                System.out.println();
                numberOfcities += 1;
            }
            for (int i = 0; i < numberOfcities; i++) {
                cityIndeces.add(i);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
