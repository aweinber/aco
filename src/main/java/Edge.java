
public class Edge{
    City city_one;
    City city_two;
    Double pheremone_level;

    public Edge(City city_one, City city_two, Double pheromone_level){
        this.city_one = city_one;
        this.city_two = city_two;
        this.pheremone_level = pheromone_level;
    }

}