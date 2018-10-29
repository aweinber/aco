import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

public class Ant {
    ArrayList<Edge> tour;
    double tour_length;
    TravellingSalespersonProblem problem;



    /**
     * Complete tour by adding cities
     */
    private void complete_tour() {


    }


    /**
     * Given remaining edges in a mapping of [edge -> probability of choosing that edge], pick the next edge
     * by picking a random probability and a random ordering of edges until the sum of those edges' probabilities
     * is greater than the random probability.
     * @param available_edges_to_probability hashmap of edge to probability
     * @return the next edge to take
     */
    private Edge pick_next_edge(HashMap<Edge, Double> available_edges_to_probability) {

        double probability = Math.random();
        double current_floor = 0.0;

        for (Edge e : available_edges_to_probability.keySet()) {
            if ( available_edges_to_probability.get(e) + current_floor > probability ) {
                return e;
            }
            current_floor += available_edges_to_probability.get(e);
        }

        return null;
    }



    private HashMap<Edge, Double> construct_probability_matrix(HashMap<Edge, Double> available_edges_to_probability,
                                                               double alpha_constant, double beta_constant) {

        double heuristic_sum = 0.0;
        for (Edge e : available_edges_to_probability.keySet()) {
            heuristic_sum += (e.getPheremone_level() * alpha_constant) * (e.length * beta_constant);
        }

        double edge_probability;
        for (Edge e : available_edges_to_probability.keySet()) {
            edge_probability = ((e.getPheremone_level() * alpha_constant) * (e.length * beta_constant)) / heuristic_sum;
            available_edges_to_probability.put(e, edge_probability);
        }
        return available_edges_to_probability;
    }


    /**
     * Update pheremone levels for every edge in the tour
     */
    private void update_pheremone_level(double evaporation_factor, double constant_factor) {
        for (Edge e : tour) {
            double old_p, new_p;
            old_p = e.getPheremone_level();
            new_p = old_p + (constant_factor / e.length);
            e.setPheremone_level(new_p);
        }
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
