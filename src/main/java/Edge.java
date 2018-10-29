import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Edge{

    public static void main(){

        ArrayList<City> cities= readCities();
        for(int i = 0; i < cities.size(); i++){
            System.out.print(cities.get(i));
        }

    }

    private static ArrayList<City> readCities(String fileName){
        BufferedReader reader;

        ArrayList<City> cities = new ArrayList<City>();

        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                // read next line
                line = reader.readLine();
                string array1[]= line.split(" ");
                double x_cord = string(array1[0]);
                double y_cord = string (array1[1]);
                City city = new City(x_cord, y_cord);
                cities.add(city);


            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }
}