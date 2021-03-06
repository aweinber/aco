import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Ant {

    /**
     * TSP problem which this ant must solve
     */
    private TSP problem;

    /**
     * Tour by which ant completes TSP
     */
    ArrayList<Edge> tour = new ArrayList<Edge>();

    /**
     * Weight given to the pheromone heuristic
     */
    private double alpha_weight;

    /**
     * Weight given to the distance heuristic
     */
    private double beta_weight;

    /**
     * The probability that we choose the 'most likely' leg without
     * even considering the others at a given stop along the tour.
     */
    private static final double PROBABILITY_SELECT_BEST_LEG = .2;


    HashSet<City> remaining_cities;

    HashSet<Edge> remaining_edges;

    List<City> all_cities_list;


    public Ant(TSP problem, double alpha_weight, double beta_weight) {
        this.problem = problem;
        this.alpha_weight = alpha_weight;
        this.beta_weight = beta_weight;
    }


    /**
     * Complete a tour. Create a set of cities and edges, then pick a random city to start with.
     * While there are remaining cities in the set of cities, find all available edges from the current city,
     * randomly pick one based on the probability sets that are mapped in available_edges, then remove the
     * destination city from the list of available cities and recompute available edges from the next one.
     */
    void complete_tour() {

        tour = new ArrayList<Edge>();

        HashSet<City> remaining_cities = new HashSet<City>(problem.get_cities());

        HashSet<Edge> remaining_edges = new HashSet<Edge>(problem.get_edges());

        List<City> all_cities_list = new ArrayList<City>(remaining_cities);

        int random_index = (int) (Math.random() * remaining_cities.size());

        City first_city = all_cities_list.get(random_index);
        City current_city = first_city;


        remaining_cities.remove(current_city);

        Edge next_edge;
        City next_city;

        HashMap<Edge, Double> available_edges = new HashMap<Edge, Double>();

        while (remaining_cities.size() > 0) {

            find_edges(remaining_cities, remaining_edges, current_city, available_edges);

            available_edges = construct_probability_dictionary(available_edges);

            next_edge = pick_next_edge(available_edges);

            available_edges.clear();


            if (next_edge.city_one == current_city) next_city = next_edge.city_two;
            else next_city = next_edge.city_one; //if next_edge.city_two == current_city

            tour.add(next_edge);

            remaining_cities.remove(next_city);

            current_city = next_city;

        }

        //return home
        for (Edge e : remaining_edges) {
            if (e.city_one == first_city && e.city_two == current_city) {
                current_city = e.city_one;
                tour.add(e);
                break;
            }
            else if (e.city_two == first_city && e.city_one == first_city) {
                current_city = e.city_two;
                tour.add(e);
                break;
            }
        }

        get_tour_length();
    }

    /**
     * Builds the edges given a list of cities
     * @param remaining_cities the remaining cities to visit
     * @return all available edges
     */
    private void find_edges(HashSet<City> remaining_cities, HashSet<Edge> remaining_edges,
                                                          City current_city, HashMap<Edge, Double> available_edges ) {


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

    }


    /**
     * Given remaining edges in a mapping of [edge -> probability of choosing that edge], pick the next edge
     * by picking a random probability and a random ordering of edges until the sum of those edges' probabilities
     * is greater than the random probability.
     * @param available_edges_to_probability hashmap of edge to probability
     * @return the next edge to take
     */
    public Edge pick_next_edge(HashMap<Edge, Double> available_edges_to_probability) {

        double probability_best_leg = Math.random();
        double current_floor = 0.0;

        if (probability_best_leg < PROBABILITY_SELECT_BEST_LEG) { //case where we just pick edge w/ biggest probability

            double max_probability = -1;
            Edge most_likely_edge = available_edges_to_probability.keySet().iterator().next(); //first one
            for (Edge e : available_edges_to_probability.keySet()) {
                if (available_edges_to_probability.get(e) > max_probability) {
                    most_likely_edge = e;
                    max_probability = available_edges_to_probability.get(e);
                }
            }
            return most_likely_edge;
        }

        double probability_select_any_num = Math.random();
        for (Edge e : available_edges_to_probability.keySet()) {

            current_floor += available_edges_to_probability.get(e);

            if ( current_floor > probability_select_any_num ) {

                return e;
            }
        }
        System.out.println("About to return null. Error. Current floor: " + current_floor + ", probability: " + probability_select_any_num);
        return null;
    }


    /**
     * Given a mapping of available edges to the probabilities of their selection, reassign the probabilities
     * based on the sum of the other remaining edges' probabilities.
     * @param available_edges_to_probability Hashmap Edge -> probability of selecting that edge
     * @return reset hashMap
     */
    public HashMap<Edge, Double> construct_probability_dictionary(HashMap<Edge, Double> available_edges_to_probability) {


        double heuristic_sum = 0.0;
        for (Edge e : available_edges_to_probability.keySet()) {
            heuristic_sum += calculate_edge_probability(e);
        }

        double edge_probability_sum = 0.0; //used for debugging
        double edge_probability;

        for (Edge e : available_edges_to_probability.keySet()) {
            edge_probability = calculate_edge_probability(e) / heuristic_sum;
            available_edges_to_probability.put(e, edge_probability);
        }
        return available_edges_to_probability;
    }

    public double calculate_edge_probability(Edge e) {
        return (Math.pow(e.getPheremone_level(), alpha_weight) * Math.pow((1/ e.length), beta_weight));
    }

    /**
     * Given a tour, set the ant's tour to be the parameter
     * @param tour
     */
    public void set_tour(ArrayList<Edge> tour){
        this.tour = tour;
        //for (Edge item : tour) this.tour.add(item);
    }


    /**
     * Update pheremone levels for every edge in the tour
     * Only used in EAS
     */
    public void update_pheromone_level() {
        double tour_length = get_tour_length();
        for (Edge e : tour) {
            e.setPheremone_level( e.pheremone_level + (1 / tour_length));
        }
    }

    /**
     * Push pheromone level back toward initial
     * Only used in ACS
     * @param epsilon degree to which pheromone level returns home
     */
    void evaporate_and_update_pheromone_level(double epsilon) {
        for (Edge e: tour) {
            e.setPheremone_level(((1 - epsilon)* e.pheremone_level) + (epsilon*e.initial_phermone));
        }
    }


    /**
     * Compute the distance of any given tour
     */
    double get_tour_length() {
        double counter = 0;
        for (Edge e : tour) {
            counter += e.length;
        }
        return counter;
    }
}
