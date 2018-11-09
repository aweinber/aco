/*
    Keeps track of the citys and the various information needed
 */

public class City {
    public Double xcord;
    public Double ycord;
    public int city_index;

    public City(Double xcord, Double ycord, int city_index) {
        this.xcord = xcord;
        this.ycord = ycord;
        this.city_index = city_index;


    }

    public String toString() {
        return "(" + xcord + ", " + ycord + ")";
    }





}

