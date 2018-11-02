public class NewEdge {

    double length;
    double pheremone_level;
    public int city_one_index;
    public int city_two_index;

    public NewEdge(int city_one_index, int city_two_index, int weight){
        this.city_one_index = city_one_index;
        this.city_two_index = city_two_index;
        this.length = weight;

    }
}
