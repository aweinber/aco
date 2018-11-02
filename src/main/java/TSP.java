import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class TSP {

    /* the name of the string  and filename that we will be using */

    /* initial constructor for TSP instance */
//    public TSP(String filename){
//        this.filename = filename;
//    }


    /* make global variables to create the TSP problem */
    ArrayList<Edge> edges;
    ArrayList<City> cities;
    ArrayList<City> remaining_cities;
    ArrayList<Edge> tour = new ArrayList<Edge>();

    public TSP(String filename, int num_ants) {
        this.cities = read_cities(filename);
        this.edges = create_edges(cities);
        this.remaining_cities = getCities();
        set_pheremone_initial(num_ants);
    }

    /*  creates all of from each city to each other city so that function will */
    public ArrayList<Edge> create_edges(ArrayList<City> cities) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        Edge edge;
        for (int i = 0; i < cities.size() - 1; i++) {
            for (int j = i + 1; j < cities.size(); j++) {
                edge = new Edge(cities.get(i), cities.get(j));
                edges.add(edge);
                cities.remove(i);
            }
        }
        return edges;
    }


    /* This function calculates the length of a tour using the nearest neighbor tour completion and uses that
        for the implementation of initial pheremone.
     */
    public void set_pheremone_initial(int num_ants) {

        double phermone_rate;
        int random_index = (int) (Math.random() * cities.size());

        double total_distance = 0;

        /* set the first city equal to the index and curr city as well */
        City first_city = cities.get(random_index);
        City current_city = first_city;
        /* remove the city so that you dont make an edge containg itself */
        remaining_cities.remove(current_city);
        City next_city;

        /* while there are remaining cities get the closest city to the current one */
        while (remaining_cities.size() > 0) {
            next_city = get_closest_city(current_city);
            Edge edge = new Edge(current_city, next_city);
            tour.add(edge);
            remaining_cities.remove(next_city);
            current_city = next_city;
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
        }




    }

    private double Distance(City one, City two){
        return Math.hypot(one.xcord - two.xcord, one.ycord - two.ycord);



    }


    public City get_closest_city(City curr){
        double best_distance = 100000000;
        City best_city = new City(0.0, 0.0);
        for(City e: remaining_cities){
            double distance = Distance(curr, e);
            if(distance < best_distance){
                best_distance = distance;
                best_city.xcord = e.xcord;
                best_city.ycord = e.ycord;
            }

        }
        return best_city;
    }



    /*
        This function parses the file and return all of the x,y coordinate pairs
        int a list of cities.
     */
    private ArrayList<City> read_cities(String fileName) {
        BufferedReader reader;
        //Set new object
        ArrayList<City> cities = new ArrayList<City>();
        try {
            //set reader to read lines
            reader = new BufferedReader(new FileReader(fileName));
            //new line
            String line = reader.readLine();
            //while there are still lines in the file
            while (line != null) {
                if (line.length() > 0) {
                    if (Character.isDigit(line.charAt(1))) {

                        //split line by space
                        String array1[] = line.split(" ");
                        Double x_cord = Double.parseDouble(array1[2]);
                        Double y_cord = Double.parseDouble(array1[3]);

                        //create new city instance from
                        City city = new City(x_cord, y_cord);
                        cities.add(city);

                        //read next line after youve been working on it
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.print("error " + e);
            //e.printStackTrace();
        }
        return cities;
    }

    public ArrayList<Edge> get_edges() {
        return edges;
    }

    public ArrayList<City> get_cities() {
        return this.cities;
    }

    public ArrayList<Edge> get_Edges() {
        return this.edges;
    }

    public ArrayList<City> getCities() {
        return cities;
    }
}

