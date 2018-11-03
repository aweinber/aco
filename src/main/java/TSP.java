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
        edges = read_cities(filename);
        remaining_edges = getEdges();
        set_pheremone_initial(num_ants, edges);
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
                double length = Distance(curr_city, e);
                edge = new Edge(curr_city.city_index, e.city_index, length);
                edges.add(edge);
            }
            curr_city = remaining_cities.get(0);
            remaining_cities.remove(curr_city);
        }
        return edges;

    }


    /* This function calculates the length of a tour using the nearest neighbor tour completion and uses that
        for the implementation of initial pheromone.
     */
    public void set_pheremone_initial(int num_ants, ArrayList<Edge> edges) {
        ArrayList<Edge> remaining_edges = new ArrayList<Edge>();
        ArrayList<Edge> tour = new ArrayList<Edge>();

        /*
            Copy the remaining edges because settign things equal in java is a reference
         */
        for(Edge e: edges){
            remaining_edges.add(e);
        }

        double phermone_rate;
        int random_index = (int) (Math.random() * remaining_edges.size());
        double total_distance = 0;

        /* set the first city equal to the index and curr city as well */
        Edge first_edge = remaining_edges.get(random_index);
        Edge current_edge;
        current_edge = first_edge;


        /* remove the city so that you dont make an edge containg itself */
        remaining_edges.remove(current_edge);
        Edge next_edge = new Edge(0, 0, 0);

        /* while there are remaining edges get the closest edge that maintains the original city */
        while (remaining_edges.size() > 0) {
            next_edge = get_closest_edge(remaining_edges, current_edge);
            remaining_edges.remove(next_edge);
            tour.add(next_edge);
            remaining_edges.remove(next_edge);

        }
        for(Edge e: edges){
            if(e.city_one_index == next_edge.city_one_index && e.city_two_index == first_edge.city_one_index){
                System.out.println("true");
                next_edge = e;
            }
        }
        tour.add(next_edge);

        /* finds total distance */
        for(Edge e: tour){
            total_distance += e.length;
        }

        phermone_rate = num_ants/total_distance;

        for(Edge e: edges){
            e.pheremone_level = phermone_rate;
        }

    }

    private double Distance(City one, City two){
        return Math.hypot(one.xcord - two.xcord, one.ycord - two.ycord);

    }


    public Edge get_closest_edge(ArrayList<Edge> remaining_edges, Edge curr){
        double best_distance = 100000000;
        Edge best_edge = new Edge(0, 0, 0);

        for(Edge e: remaining_edges){
            if(e.city_two_index == curr.city_one_index){
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
    private ArrayList<Edge> read_cities(String fileName) {
        BufferedReader reader;
        //Set new object
        ArrayList<Edge> cities = new ArrayList<Edge>();
        try {
            //set reader to read lines
            reader = new BufferedReader(new FileReader(fileName));
            //new line
            String line = reader.readLine();
            //while there are still lines in the file
            while (line != null) {
                if (line.length() > 0) {
                    System.out.println(line);
                    if (line.contains("EDGE_WEIGHT_TYPE: EXPLICIT")) {
                         cities = read_upper_row(fileName);
                         return cities;
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
        int data_display = 0;
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
                            Edge edge = new Edge(CityIndex, (CityIndex+1+i), Integer.parseInt(array[i]));
                            edges.add(edge);
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

    public ArrayList<Edge> read_euc2D(String fileName){
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
                        double x_cord = Double.parseDouble(array1[2]);
                        double y_cord = Double.parseDouble(array1[3]);
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
        edges = create_edges(cities);
        return edges;

    }


    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
}

