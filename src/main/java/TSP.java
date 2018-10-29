import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TSP {

    /* the name of the string  and filename that we will be using*/
    static String filename;

    /* initial constructor for TSP instance */
    public TSP(String filename){
        this.filename = filename;
    }


    /* make global variables to create the TSP problem */
    static ArrayList<Edge> Edges = new ArrayList<Edge>();
    static ArrayList<City> cities = new ArrayList<City>();

    public static void main() {

        cities = read_cities(filename);
        Edges = create_edges(cities);

    }

    /*  creates all of from each city to each other city so that function will */
    public static ArrayList<Edge> create_edges(ArrayList<City> cities){
        ArrayList<Edge> edges = new ArrayList<Edge>();
        Edge edge;
        for(City x: cities){
            for(City y: cities) {
                edge = new Edge(x, y, 0.0);
                edges.add(edge);
            }
        }
        return edges;
    }


    private static ArrayList<City> read_cities(String fileName){
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


                //split line by space
                String array1[]= line.split(" ");
                Double x_cord = Double.parseDouble(array1[0]);
                Double y_cord = Double.parseDouble(array1[1]);

                //create new city instance from
                City city = new City(x_cord, y_cord);
                cities.add(city);

                //read next line after youve been working on it
                line = reader.readLine();


            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }
}

