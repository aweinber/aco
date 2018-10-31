import java.util.*;

public class Ant {
    ArrayList<Edge> tour;
    double tour_length;
    TSP problem;


    Ant(TSP problem){

    }
    /**
     * Complete a tour. Create a set of cities and edges, then pick a random city to start with.
     * While there are remaining cities in the set of cities, find all available edges from the current city,
     * randomly pick one based on the probability sets that are mapped in available_edges, then remove the
     * destination city from the list of available cities and recompute available edges from the next one.
     */
    public void complete_tour() {

        HashSet<City> remaining_cities = new HashSet<City>(problem.get_cities());

        HashSet<Edge> remaining_edges = new HashSet<Edge>(problem.get_edges());

        List<City> all_cities_list = new ArrayList<City>(remaining_cities);

        int random_index = (int) Math.random() * all_cities_list.size();

        City current_city = all_cities_list.get(random_index);

        remaining_cities.remove(current_city);

        HashMap<Edge, Double> available_edges;
        Edge next_edge;
        City next_city;

        while (remaining_cities.size() > 0) {

            available_edges = find_edges_given_cities(remaining_cities, remaining_edges, current_city);

            next_edge = pick_next_edge(available_edges);

            if (next_edge.city_one == current_city) next_city = next_edge.city_two;
            else next_city = next_edge.city_one; //if (next_edge.city_two == current_city)

            tour.add(next_edge);

            remaining_cities.remove(next_city);

            current_city = next_city;

        }


    }

    /**
     * Builds the edges given a list of cities
     * @param remaining_cities the remaining cities to visit
     * @return all available edges
     */
    private HashMap<Edge, Double> find_edges_given_cities(HashSet<City> remaining_cities, HashSet<Edge> remaining_edges,
                                                          City current_city) {

        HashMap<Edge, Double> available_edges = new HashMap<Edge, Double>();

        for (Edge edge : remaining_edges) {

            if (edge.city_one == current_city) {
                if (remaining_cities.contains(edge.city_two)) {
                    available_edges.put(edge, edge.pheremone_level);
                }
            }
            if (edge.city_two == current_city) {
                if (remaining_cities.contains(edge.city_one)) {
                    available_edges.put(edge, edge.pheremone_level);
                }
            }
        }

        return available_edges;
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
            current_floor += available_edges_to_probability.get(e);
            if ( current_floor > probability ) {
                return e;
            }
        }

        return null;
    }


    /**
     * Given a mapping of available edges to the probabilities of their selection, reassign the probabilities
     * based on the sum of the other remaining edges' probabilities.
     * @param available_edges_to_probability Hashmap Edge -> probability of selecting that edge
     * @param alpha_constant influence of pheromones
     * @param beta_constant influence of length
     * @return reset hashMap
     */
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
    public void update_pheremone_level(double constant_factor) {
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
    public double get_tour_length() {
        double counter = 0;
        for (Edge e : tour) {
            counter += e.length;
        }
        this.tour_length = counter;
        return counter;
    }
}
