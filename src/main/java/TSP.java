import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class TSP {

    /* make global variables to create the TSP problem */
    ArrayList<Edge> edges;
    ArrayList<City> cities;
    double optimal;


    /*
        Initial contructor for the TSP file
     */
    public TSP(String filename, int num_ants, double optimal) {
        cities = new ArrayList<City>();
        cities = read_euc2D(filename);
        edges = create_edges(cities);
        optimal = optimal;
        //remaining_edges = get_Edges();
        set_pheremone_initial(num_ants, cities);

    }

    /*  creates all of from each city to each other city so that function will */
    public ArrayList<Edge> create_edges(ArrayList<City> cities) {

        ArrayList<City> remaining_cities = new ArrayList<City>();

        /* Copy all the cities to remaining cities is there a way to copy instead of reference */
        remaining_cities.addAll(cities);
        ArrayList<Edge> edges = new ArrayList<Edge>();
        Edge edge;

        City curr_city = remaining_cities.get(0);
        remaining_cities.remove(curr_city);

        while (remaining_cities.size() > 0){
            for (City e: remaining_cities ) {
                edge = new Edge(curr_city, e);
                edges.add(edge);
            }
            curr_city = remaining_cities.get(0);
            remaining_cities.remove(curr_city);
        }
        return edges;

    }



    public void set_pheremone_initial(int num_ants, ArrayList<City> cities) {
        ArrayList<City> remaining_cities = new ArrayList<City>();
        ArrayList<Edge> tour = new ArrayList<Edge>();

        for(City e: cities){
            remaining_cities.add(e);
        }

        double phermone_rate;
        //int random_index = (int) (Math.random() * remaining_cities.size());
        double total_distance = 0;

        /* set the first city equal to the index and curr city as well */
        City first_city = remaining_cities.get(0);
        City current_city;
        current_city = first_city;

        /* remove the city so that you don't make an edge containg itself */
        remaining_cities.remove(current_city);

        City next_city;
        /* while there are remaining cities get the closest city to the current one */
        while (remaining_cities.size() > 0) {
            next_city = get_closest_city(remaining_cities, current_city);
            remaining_cities.remove(next_city);
            Edge edge = new Edge(current_city, next_city);
            tour.add(edge);
            remaining_cities.remove(next_city);

        }
        Edge edge = new Edge(current_city, first_city);
        tour.add(edge);

        /* finds total distance */
        for(Edge e: tour){
            total_distance += e.length;
        }
        phermone_rate = num_ants/total_distance;

        for(Edge e: edges){
            e.pheremone_level = phermone_rate;
            e.initial_phermone = phermone_rate;
        }

    }

    public City get_closest_city(ArrayList<City> remaining_cities, City curr){
        double best_distance = 100000000;
        City best_city = new City(0.0, 0.0, 0);
        for(City e: remaining_cities){
            double distance = Distance(curr, e);
            if(distance < best_distance){
                best_distance = distance;
                best_city = e;
            }

        }
        return best_city;
    }

    private double Distance(City one, City two){
        return Math.hypot(one.xcord - two.xcord, one.ycord - two.ycord);



    }

    /**
     * Specific to problems that only give you the weight of the original file
     */

    public ArrayList<City> read_euc2D(String fileName){
        ArrayList<City> cities = new ArrayList<City>();
        ArrayList<Edge> edges;
        BufferedReader reader;
        try {
            //set reader to read lines
            reader = new BufferedReader(new FileReader(fileName));
            //new line
            String line = reader.readLine();
            //while there are still lines in the file
            while (line != null) {
                if (line.length() > 0) {
                    if(Character.isDigit(line.charAt(0)) ) {

                        line = line.replaceAll("  ", " ");

                        //split line by space
                        String array1[] = line.split(" ");
                        double x_cord = Double.parseDouble(array1[1]);
                        double y_cord = Double.parseDouble(array1[2]);
                        int city_index = Integer.parseInt(array1[0]);

                        City city = new City(x_cord, y_cord, city_index);
                        cities.add(city);
                    }
                }
                line = reader.readLine();
            }
        }
        catch(IOException e) {
            System.out.print("error " + e);
        }
        return cities;

    }

    public ArrayList<Edge> get_edges() {
        return this.edges;
    }
    public ArrayList<City> get_cities() {
        return cities;
    }
}

