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

            TSP problem = new TSP("ulysses16.tsp", num_ants);

            System.out.println("EAS alogrithm");
            EAS eas = new EAS(problem, num_ants, num_iterations, alpha, beta, evaporation_factor, elitism_factor);
            eas.execute_eas();

            problem = new TSP("ulysses16.tsp", num_ants);

            System.out.println("\nACO alogrithm");
            ACO aco = new ACO(problem, num_ants, num_iterations, alpha, beta, evaporation_factor);
            aco.execute_aco();

            System.out.println("Best length: " + aco.get_best_length());

        }
    }

    public static void test_ant_construct_dictionary_method(int num_ants) {
        TSP problem = new TSP("prl124.tsp", num_ants);
        Ant ant = new Ant(problem);

        City city1 = new City(1.0, 1.0, 1);
        City city2 = new City(2.0, 2.0, 2);
        City city3 = new City(100.0, 100.0, 3);

        Edge e1 = new Edge(city1, city2);
        Edge e2 = new Edge(city2, city3);
        Edge e3 = new Edge(city3, city2);

        HashMap<Edge, Double> testMap = new HashMap<Edge, Double>();
        testMap.put(e1, 0.0);
        testMap.put(e2, 0.0);
        testMap.put(e3, 0.0);

//        testMap = ant.construct_probability_dictionary(testMap);

        Ant a1 = new Ant(problem);
        Ant a2 = new Ant(problem);

        a1.complete_tour();
        System.out.println("A1: " + a1.get_tour_length());

        a2.complete_tour();
        System.out.println("A2: " + a2.get_tour_length());



    }
}
