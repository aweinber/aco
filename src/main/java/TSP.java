import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class TSP {

    /* make global variables to create the TSP problem */
    ArrayList<Edge> edges;
    ArrayList<City> cities;
    ArrayList<Edge> remaining_edges;
    double pheremone_initial;


    /*
        Initial contructor for the TSP file
     */
    public TSP(String filename, int num_ants) {
        cities = new ArrayList<City>();
        cities = read_cities(filename);
        edges = create_edges(cities);
        //remaining_edges = get_Edges();
        System.out.println("Num edges: " + edges.size());
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

//        while (remaining_cities.size() > 0){
//            for (City e: remaining_cities ) {
//                double length = Distance(curr_city, e);
//                edge = new Edge(curr_city.city_index, e.city_index, length);
//                edges.add(edge);
//            }
//            curr_city = remaining_cities.get(0);
//            remaining_cities.remove(curr_city);
//        }
        while (remaining_cities.size() > 0){
            for (City e: remaining_cities ) {
                double length = Distance(curr_city, e);
                edge = new Edge(curr_city, e);
                edges.add(edge);
            }
            curr_city = remaining_cities.get(0);
            remaining_cities.remove(curr_city);
        }
        return edges;

    }

    private double Distance(City one, City two){
        return Math.hypot(one.xcord - two.xcord, one.ycord - two.ycord);

    }




    /* This function calculates the length of a tour using the nearest neighbor tour completion and uses that
        for the implementation of initial pheromone.
     */
//    public void set_pheremone_initial(int num_ants, ArrayList<Edge> edges) {
//        ArrayList<Edge> remaining_edges = new ArrayList<Edge>();
//        ArrayList<Edge> tour = new ArrayList<Edge>();
//
//        /*
//            Copy the remaining edges because settign things equal in java is a reference
//         */
//        for(Edge e: edges){
//            remaining_edges.add(e);
//        }
//
//        double phermone_rate;
//        int random_index = (int) (Math.random() * remaining_edges.size());
//        double total_distance = 0;
//
//        /* set the first city equal to the index and curr city as well */
//        Edge first_edge = remaining_edges.get(random_index);
//        Edge current_edge;
//        current_edge = first_edge;
//
//
//        /* remove the city so that you dont make an edge containg itself */
//        remaining_edges.remove(current_edge);
//        City city_one = new City(0.0, 0.0, 0);
//        City city_two= new City(0.0, 0.0, 0);
//        Edge next_edge = new Edge(city_one, city_two);
//
//        /* while there are remaining edges get the closest edge that maintains the original city */
//        while (first_edge.city_one != current_edge.city_two){
//            next_edge = get_closest_edge(remaining_edges, current_edge);
//            remaining_edges.remove(next_edge);
//            tour.add(next_edge);
//            remaining_edges.remove(next_edge);
//            current_edge = next_edge;
//
//        }
//        for(Edge e: edges){
//            if(e.city_one == next_edge.city_one && e.city_two == first_edge.city_two){
//                System.out.println("true");
//                next_edge = e;
//            }
//        }
//        tour.add(next_edge);
//
//        /* finds total distance */
//        for(Edge e: tour){
//            total_distance += e.length;
//        }
//
//        phermone_rate = num_ants/total_distance;
//
//        for(Edge e: edges){
//            e.pheremone_level = phermone_rate;
//        }
//
//    }

    public void set_pheremone_initial(int num_ants, ArrayList<City> cities) {
        ArrayList<City> remaining_cities = new ArrayList<City>();
        ArrayList<Edge> tour = new ArrayList<Edge>();
        for(City e: cities){
            remaining_cities.add(e);
        }

        double phermone_rate;
        int random_index = (int) (Math.random() * remaining_cities.size());

        double total_distance = 0;

        /* set the first city equal to the index and curr city as well */
        City first_city = remaining_cities.get(random_index);
        City current_city;
        current_city = first_city;

        /* remove the city so that you dont make an edge containg itself */
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



    public Edge get_closest_edge(ArrayList<Edge> remaining_edges, Edge curr){
        double best_distance = 100000000;
        City city_one = new City(0.0,0.0,0);
        City city_two = new City(0.0,0.0,0);
        Edge best_edge = new Edge(city_one, city_two);

        for(Edge e: remaining_edges){
            if(e.city_one == curr.city_two){
                if(e.length < best_distance) {
                    best_distance = e.length;
                    best_edge = e;
                }
            }

        }
        return best_edge;
    }


    /*
        This function parses the file and return all of the x,y coordinate pairs
        int a list of cities.
     */
    private ArrayList<City> read_cities(String fileName) {
        BufferedReader reader;
        //Set new object
        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<City> cities = new ArrayList<City>();
        try {
            //set reader to read lines
            reader = new BufferedReader(new FileReader(fileName));
            //new line
            String line = reader.readLine();
            //while there are still lines in the file
            while (line != null) {
                if (line.length() > 0) {
                    if (line.contains("EDGE_WEIGHT_TYPE: EXPLICIT")) {
                         edges = read_upper_row(fileName);
                    }
                }
                line = reader.readLine();
            }
            reader.close();
            cities = read_euc2D(fileName);
        } catch (IOException e) {
            System.out.print("error " + e);
            //e.printStackTrace();
        }
        return cities;
    }

    /*
        Specific to problems that only give you the weight of the original file
      */

    public ArrayList<Edge> read_upper_row(String filename){
        ArrayList<Edge> edges = new ArrayList<Edge>();
        BufferedReader reader;
        try {
            //set reader to read lines
            reader = new BufferedReader(new FileReader(filename));
            //new line
            String line = reader.readLine();
            //while there are still lines in the file
            int CityIndex = 1;

            //while there are more lines
            while (line != null) {
                //if it is not empty
                if (line.length() > 0) {

                    //check to see if its number
                    if(Character.isDigit(line.charAt(1))) {

                        //if there is whitespace ot two spaces change that
                        if(Character.isWhitespace(line.charAt(0))){
                            line = line.replaceFirst(" ", "");
                        }
                        line = line.replaceAll("  ", " ");

                        //split array
                        String[] array = line.split(" ");

                        //set the city index
                        for(int i = 0; i < array.length; i ++){
                            System.out.print(array[i] + " ");
                            //Edge edge = new Edge(CityIndex, (CityIndex+1+i), Integer.parseInt(array[i]));
                            //edges.add(edge);
                        }
                    }
                    CityIndex +=1;
                    //System.out.println(" ");
                }
                line = reader.readLine();
            }
        }
        catch(IOException e) {
            System.out.print("error " + e);
            //e.printStackTrace();
        }
        return edges;

    }

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
                    if(Character.isDigit(line.charAt(1)) ) {

                        if(Character.isWhitespace(line.charAt(0))){
                            line = line.replaceFirst(" ", "");
                        }
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
            //e.printStackTrace();
        }
        return cities;

    }



    public ArrayList<Edge> get_edges() {
        return this.edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public ArrayList<City> get_cities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }
}

