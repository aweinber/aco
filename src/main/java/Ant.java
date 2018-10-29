import java.util.ArrayList;

public class Ant {
    ArrayList<Edge> tour;
    double tour_length;
    TSP problem;



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
