import java.util.HashMap;

public class Test{

    public static int num_ants;
    public static int num_iterations;
    public static double alpha;
    public static double beta;
    public static double evaporation_factor;
    public static int  elitism_factor;
    public static double epsilon;


    public static void main(String[] args) {

        if(args.length > 0) {
            num_ants = Integer.parseInt(args[1]);
            num_iterations = Integer.parseInt(args[2]);
            alpha = Double.parseDouble(args[3]);
            beta = Double.parseDouble(args[4]);
            evaporation_factor = Double.parseDouble(args[5]);
            elitism_factor = Integer.parseInt(args[6]);
            epsilon = Double.parseDouble(args[7]);
        }

        else{

            num_ants = 10;
            num_iterations = 100;
            alpha = 1;
            beta = 2;
            evaporation_factor = 0.1;
            elitism_factor = num_ants;
            epsilon = 0.1;


            test_ant_construct_dictionary_method();

        }
    }

    public static void test_ant_construct_dictionary_method() {
        TSP problem = new TSP("ulysses16.tsp");
        Ant ant = new Ant(problem);

        City city1 = new City(1.0, 1.0);
        City city2 = new City(2.0, 2.0);
        City city3 = new City(100.0, 100.0);

        Edge e1 = new Edge(city1, city2, 50.0);
        Edge e2 = new Edge(city2, city3, 5.0);
        Edge e3 = new Edge(city3, city2, 50.0);

        HashMap<Edge, Double> testMap = new HashMap<Edge, Double>();
        testMap.put(e1, 0.0);
        testMap.put(e2, 0.0);
        testMap.put(e3, 0.0);
        testMap = ant.construct_probability_dictionary(testMap);



        ant.complete_tour();


    }
}
