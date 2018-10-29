
public class Edge{
    City city_one;
    City city_two;
    double length;
    private double pheremone_level;

    public Edge(City city_one, City city_two, double length, double pheromone_level){
        this.city_one = city_one;
        this.city_two = city_two;
        this.length = length;
        this.pheremone_level = pheromone_level;

    }

}