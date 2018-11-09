import java.lang.Math;

public class Edge{
    City city_one;
    City city_two;
    double length;
    double pheremone_level;
    public double initial_phermone;

    public Edge(City city_one, City city_two){
        this.city_one = city_one;
        this.city_two = city_two;
        this.length = Distance(this.city_one, this.city_two);


    }

//

    private double Distance(City one, City two){
        return Math.hypot(one.xcord - two.xcord, one.ycord - two.ycord);



    }

    public double getPheremone_level() {
        return pheremone_level;
    }

    public void setPheremone_level(double pheremone_level) {
        this.pheremone_level = pheremone_level;
    }

    public String toString() {
        return "Edge from " + city_one + " to " + city_two;
    }
}