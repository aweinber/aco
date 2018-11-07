import java.util.ArrayList;

public class EAS extends AntSystem{

  private double evaporation_rate;
  private double elitism;
  public int best_iter= 0;
  public long endtime;

  /**
   * Initializes the EAS problem.
  */ 
  EAS(TSP problem, int num_ants, int max_iterations, double alpha, double beta, double evaporation_rate, double elitism, int termination_condition, long endtime){
    super(problem, num_ants, alpha, beta, max_iterations, termination_condition);
    super.create_colony();
    this.endtime = endtime;
    this.elitism = elitism;
    this.evaporation_rate = evaporation_rate;
    super.best = new Ant(this.problem, this.alpha, this.beta);
    super.best.tour = new ArrayList<Edge>();
  };

  /**
   * Walk through steps of eco -- while termination condition is not met,
   * move the colony, update pheromones, and set a new best tour if one is found.
   */
  void execute_eas(long endtime) {
    System.out.println("in execute eas");

    int num_iter = 0;

    while (!super.should_terminate(num_iter)) {

      move_eas(endtime);
      if(System.currentTimeMillis() > endtime){
        return;
      }
      pheromone_evaporation(evaporation_rate);

      for(int x = 0; x < this.num_ants; x++){
        if(x == 0 && num_iter == 0){
          super.best.set_tour(colony[x].tour);
        }
        else if(colony[x].get_tour_length() < best.get_tour_length()){

          super.best.set_tour(colony[x].tour);
          best_iter = num_iter;
        }
        colony[x].update_pheromone_level();
      }

      if(System.currentTimeMillis() > endtime){
        return;
      }

      update_best_found_so_far_pheromone(elitism);
      num_iter++;
    }
    System.out.println("Terminating at number iteration " + num_iter);
  }


  /**
   * Completes tour for every ant in the colony
   */
  private void move_eas(long endtime) {
      for (int i = 0; i < num_ants; i++) {
        if(System.currentTimeMillis() > endtime){
          return;
        }
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

