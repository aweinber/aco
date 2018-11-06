import java.util.HashMap;

public class Test{

    public static int num_ants;
    public static int max_iterations;
    public static double alpha;
    public static double beta;
    public static double evaporation_factor;
    public static int  elitism_factor;
    public static double epsilon;


    public static void main(String[] args) {

        if(args.length > 0) {
            num_ants = Integer.parseInt(args[1]);
            max_iterations = Integer.parseInt(args[2]);
            alpha = Double.parseDouble(args[3]);
            beta = Double.parseDouble(args[4]);
            evaporation_factor = Double.parseDouble(args[5]);
            elitism_factor = Integer.parseInt(args[6]);
            epsilon = Double.parseDouble(args[7]);
        }

        else{

            num_ants = 20;
            max_iterations = 500;
            alpha = 1;
            beta = 2;
            evaporation_factor = 0.1;
            elitism_factor = num_ants;
            epsilon = 0.1;

            TSP problem = new TSP("eil51.tsp", num_ants ,426.0);

            int termination_condition = 3;


            EAS eas = new EAS(problem, num_ants, max_iterations, alpha, beta, evaporation_factor, elitism_factor, termination_condition);
            eas.execute_eas();
            System.out.println("EAS best: " + eas.get_best_length());


            problem = new TSP("eil51.tsp", num_ants, 426.0);

            System.out.println("ACO alogrithm");
            ACO aco = new ACO(problem, num_ants, max_iterations, alpha, beta, evaporation_factor, epsilon, termination_condition);
            aco.execute_aco();

            System.out.println("Best length: " + aco.get_best_length());


        }
    }

    public static void testing(){
        num_ants = 30;
        max_iterations = 500;
        alpha = 1;
        beta = 2;
        evaporation_factor = 0.1;
        elitism_factor = num_ants;
        epsilon = 0.1;
        double[] alpha_changes = {0, .25, .5, 1, 2};
        double[] beta_changes = {0, 2, 4, 6, 8};
        double dnum_ants = num_ants;
        double[] elitism_factor_changes = {dnum_ants/4, dnum_ants/2, dnum_ants, dnum_ants*2, dnum_ants*4};
        double[] epsilon_changes = {0, .001, .1, .25, .5, .1};

        for(int i = 0; i < alpha_changes.length; i++){
            double avg_time = 0;
            double avg_best_iterations = 0;
            double avg_best_length = 0;
            for(int j = 0; j < 10; j++) {

                System.out.println("----------------------------------------------");
                System.out.println("Running alpha changes");
                System.out.println("----------------------------------------------");

                TSP problem = new TSP("eil51.tsp", num_ants, 426.0);
                int termination_condition = 3;

                EAS eas = new EAS(problem, num_ants, max_iterations, alpha_changes[i], beta, evaporation_factor, elitism_factor, termination_condition);

                long startTime = System.nanoTime();
                eas.execute_eas();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 100;
                avg_time += duration;
                avg_best_iterations += eas.best_iter;
                avg_best_length += eas.get_best_length();
            }
            System.out.println("Average time take to execute eas:  " + (avg_time/10));
            System.out.println("Eas with this variable for alpha:  " + alpha_changes[i]);
            System.out.println("avg iteration where max was found:  " + avg_best_iterations/10);
            System.out.println("EAS best length average over 10 iterations: " + avg_best_length/10);
            System.out.println();

        }
        for (int i = 0; i < beta_changes.length; i++) {
            double avg_time = 0;
            double avg_best_iterations = 0;
            double avg_best_length = 0;
            for(int j = 0; j < 10; j++) {
                System.out.println("----------------------------------------------");
                System.out.println("Running beta changes");
                System.out.println("----------------------------------------------");

                TSP problem = new TSP("eil51.tsp", num_ants, 426.0);
                int termination_condition = 3;

                EAS eas = new EAS(problem, num_ants, max_iterations, alpha, beta_changes[i], evaporation_factor, elitism_factor, termination_condition);

                long startTime = System.nanoTime();
                eas.execute_eas();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 100;
                avg_time += duration;
                avg_best_iterations += eas.best_iter;
                avg_best_length+= eas.get_best_length();


            }
            System.out.println("Average time take to execute eas:  " + (avg_time/10));
            System.out.println("Eas with this variable for alpha:  " + beta_changes[i]);
            System.out.println("avg iteration where max was found:  " + avg_best_iterations/10);
            System.out.println("EAS best length average over 10 iterations: " + avg_best_length/10);
            System.out.println();
        }
        for (int i = 0; i < elitism_factor_changes.length; i++) {
            double avg_time = 0;
            double avg_best_iterations = 0;
            double avg_best_length = 0;
            for(int j = 0; j < 10; j++) {

                System.out.println("----------------------------------------------");
                System.out.println("Elitism changes");
                System.out.println("----------------------------------------------");

                TSP problem = new TSP("eil51.tsp", num_ants, 426.0);
                int termination_condition = 3;

                EAS eas = new EAS(problem, num_ants, max_iterations, alpha, beta, evaporation_factor, elitism_factor_changes[i], termination_condition);

                long startTime = System.nanoTime();
                eas.execute_eas();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 100;
                avg_time += duration;
                avg_best_iterations += eas.best_iter;
                avg_best_length+= eas.get_best_length();

            }
            System.out.println("Average time take to execute eas:  " + (avg_time/10));
            System.out.println("Eas with this variable for elitism factor:  " + elitism_factor_changes[i]);
            System.out.println("avg iteration where max was found:  " + avg_best_iterations/10);
            System.out.println("EAS best length average over 10 iterations: " + avg_best_length/10);
            System.out.println();

        }

        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");
        System.out.println("Now Printing ACO results");
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");


        for(int i = 0; i < alpha_changes.length; i++){
            double avg_time = 0;
            double avg_best_iterations = 0;
            double avg_best_length = 0;
            for(int j = 0; j < 10; j++) {

                System.out.println("----------------------------------------------");
                System.out.println("Running alpha changes");
                System.out.println("----------------------------------------------");

                TSP problem = new TSP("eil51.tsp", num_ants, 426.0);
                int termination_condition = 3;

                ACO aco = new ACO(problem, num_ants, max_iterations, alpha_changes[i], beta, evaporation_factor, epsilon, termination_condition);

                long startTime = System.nanoTime();
                aco.execute_aco();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 100;

                avg_time += duration;
                avg_best_iterations += aco.best_iter;
                avg_best_length += aco.get_best_length();
            }
            System.out.println();
            System.out.println("Average time taken to execute aco in seconds:  " + avg_time/10);
            System.out.println("ACo with this variable for beta:  " + alpha_changes[i]);
            System.out.println("Average iteration where max was found:  " + avg_best_iterations/10);
            System.out.println("Average ACO Best: " + avg_best_length/10);
            System.out.println();


        }

        for(int i = 0; i < beta_changes.length; i++){
            double avg_time = 0;
            double avg_best_iterations = 0;
            double avg_best_length = 0;
            for(int j = 0; j < 10; j++) {
                System.out.println("----------------------------------------------");
                System.out.println("Running beta changes");
                System.out.println("----------------------------------------------");

                TSP problem = new TSP("eil51.tsp", num_ants, 426.0);
                int termination_condition = 3;

                ACO aco = new ACO(problem, num_ants, max_iterations, alpha, beta_changes[i], evaporation_factor, epsilon, termination_condition);

                long startTime = System.nanoTime();
                aco.execute_aco();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 100;
                avg_time += duration;
                avg_best_iterations += aco.best_iter;
                avg_best_length += aco.get_best_length();
            }

            System.out.println();
            System.out.println("Average time taken to execute aco in seconds:  " + avg_time/10);
            System.out.println("ACo with this variable for beta:  " + beta_changes[i]);
            System.out.println("Average iteration where max was found:  " + avg_best_iterations/10);
            System.out.println("Average ACO Best: " + avg_best_length/10);
            System.out.println();


        }

        for(int i = 0; i < epsilon_changes.length; i++){
            double avg_time = 0;
            double avg_best_iterations = 0;
            double avg_best_length = 0;
            
            for(int j = 0; j < 10; j++) {

                System.out.println("----------------------------------------------");
                System.out.println("Epsilon changes");
                System.out.println("----------------------------------------------");

                TSP problem = new TSP("eil51.tsp", num_ants, 426.0);
                int termination_condition = 3;

                ACO aco = new ACO(problem, num_ants, max_iterations, alpha, beta, evaporation_factor, epsilon_changes[i], termination_condition);

                long startTime = System.nanoTime();
                aco.execute_aco();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 100;
                avg_time += duration;
                avg_best_iterations += aco.best_iter;
                avg_best_length += aco.get_best_length();
            }
            
            System.out.println();
            System.out.println("Average time taken to execute aco in seconds:  " + avg_time/10);
            System.out.println("ACO with this variable for Epsilo :  " + epsilon_changes[i]);
            System.out.println("Average iteration where max was found:  " + avg_best_iterations/10);
            System.out.println("Average ACO Best: " + avg_best_length/10);
            System.out.println();

        }

    }







}
