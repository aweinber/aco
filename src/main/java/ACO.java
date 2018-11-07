import java.util.ArrayList;

public class ACO extends AntSystem{

  private double evaporation_rate;
  private double epsilon;
  int best_iter = 0;
  long endTime;




  /**
   * Initializes the ACO problem.
  */ 
  ACO(TSP problem, int num_ants, int max_iterations, double alpha, double beta, double evaporation_rate, double epsilon, int termination_condition, long endtime){
    super(problem, num_ants, alpha, beta, max_iterations, termination_condition);

    this.evaporation_rate = evaporation_rate;
    this.epsilon = epsilon;
    super.create_colony();
    this.endTime = endtime;

    super.best = new Ant(this.problem, this.alpha, this.beta);
    super.best.tour = new ArrayList<Edge>();

  };

  /**
   * Walk through steps of aco -- while termination condition is not met,
   * move the colony, update pheromones, and set a new best tour if one is found.
   */
  void execute_aco(long endTime) {

    int num_iter = 0;

    while (!super.should_terminate(num_iter)) {
      if(System.currentTimeMillis() > endTime){
        return;
      }
      move_aco(endTime);
      super.pheromone_evaporation(evaporation_rate);

      for(int x = 0; x < this.num_ants; x++){
        if(x == 0 && num_iter == 0){
          super.best.set_tour(colony[x].tour);
        }
        else if(colony[x].get_tour_length() < best.get_tour_length()){
          super.best.set_tour(colony[x].tour);
          best_iter = num_iter;
        }
      }
      if(System.currentTimeMillis() > endTime){
        return;
      }

      update_best_found_so_far_pheromone(evaporation_rate);
      num_iter++;
    }
    System.out.println("Terminating at number iteration " + num_iter);
  }

  /**
   * Moves aco system for every ant. Evaporates after by epsilon.
   */
  private void move_aco(long endTime) {
    for(int i = 0; i < num_ants; i++){
      if(System.currentTimeMillis() > endTime){
        return;
      }
      super.colony[i].complete_tour();
      super.colony[i].evaporate_and_update_pheromone_level(this.epsilon);
    }
  }

  /**
   * Updates best found so far
   * @param evaporation_rate rate at which it evaporates
   */
  protected void update_best_found_so_far_pheromone(double evaporation_rate){
    double best_length = super.best.get_tour_length();
    for(Edge e: best.tour){
      double old_p, new_p;
      old_p = e.getPheremone_level();
      new_p = old_p + (evaporation_rate / best_length);
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
