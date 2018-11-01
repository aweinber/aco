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

        }
    }
}
