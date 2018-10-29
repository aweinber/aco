import java.util.ArrayList;

public class Ant {
    ArrayList<Edge> tour;
    double tour_length;
    TravellingSalespersonProblem problem;

    public Edge(ArrayList<Edge> tour, TravellingSalespersonProblem problem) {
        this.tour = tour;
        this.problem = problem;
    }

    public Edge(TravellingSalespersonProblem problem) {
        this.problem = problem;
    }

    /**
     * Complete tour by adding cities
     */
    private void complete_tour() {

    }

    /**
     * Update pheremone levels for every edge in the tour
     */
    private void update_pheremone_level() {

    }



    /**
     * Compute the distance of any given tour
     */
    private double get_tour_length() {
        double counter = 0;
        for (Edge e : tour) {
            counter += e.length;
        }
        this.tour_length = counter;
        return counter;
    }
}
