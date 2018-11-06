import java.util.ArrayList;

public class EAS extends AntSystem{

  private double evaporation_rate;
  private double elitism;


  EAS(TSP problem, int num_ants, int max_iterations, double alpha, double beta, double evaporation_rate, double elitism, int termination_condition){
    super(problem, num_ants, alpha, beta, max_iterations, termination_condition);
    super.create_colony();
    this.elitism = elitism;
    this.evaporation_rate = evaporation_rate;

    super.best = new Ant(this.problem, this.alpha, this.beta);
    super.best.tour = new ArrayList<Edge>();
  }


  void execute_eas() {

    int num_iter = 0;

    while (!super.should_terminate(num_iter)) {

      move_eas();
      pheromone_evaporation(evaporation_rate);

      for(int x = 0; x < this.num_ants; x++){
        if(x == 0 && num_iter == 0){
          super.best.set_tour(colony[x].tour);
        }
        else if(colony[x].get_tour_length() < best.get_tour_length()){

          super.best.set_tour(colony[x].tour);
        }
        colony[x].update_pheromone_level();
      }

      update_best_found_so_far_pheromone(elitism);

      num_iter++;
    }
    System.out.println("Terminating at number iteration " + num_iter);
  }


  /**
   * Completes tour for every ant in the colony
   */
  private void move_eas(){
    for(int i = 0; i < num_ants; i++){
      colony[i].complete_tour();
    }
  }

  /**
   * Updates the pheromones on the edges of the best tour so far
   * @param elitism factor
   */
  private void update_best_found_so_far_pheromone(double elitism){
    double best_length = best.get_tour_length();
    for(Edge e: best.tour){
      double old_p, new_p;
      old_p = e.getPheremone_level();
      new_p = old_p + (elitism / best_length);
      e.setPheremone_level(new_p);
    }
  }


  /**
   * @return best length
   */
  public double get_best_length(){
    return best.get_tour_length();
  }

}

