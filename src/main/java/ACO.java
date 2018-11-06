import java.util.*;

public class ACO extends AntSystem{

  private double evaporation_rate;
  private Ant best;
  private double epsilon;


  int max_iterations = 100;


  double percentage_from_optimal = 20;

  private static final int TERMINATE_AT_NUM_ITER = 1;
  private static final int TERMINATE_AT_PERCENTAGE_FROM_OPTIMAL = 2;
  private static final int TERMINATE_AT_FIRST_CONDITION = 3;

  private int termination_condition;


  ACO(TSP problem, int num_ants, int max_iterations, double alpha, double beta, double evaporation_rate, double epsilon, int termination_condition){
    super(problem, num_ants, alpha, beta, max_iterations, termination_condition);
    this.best = new Ant(this.problem, this.alpha, this.beta);
    this.evaporation_rate = evaporation_rate;
    this.termination_condition = termination_condition;
    this.epsilon = epsilon;
    super.create_colony();
  }

  public void execute_aco() {

    int num_iter = 0;

    while (!super.should_terminate(num_iter)) {

      move_aco(colony);
      super.pheremone_evaporation(evaporation_rate);

      for(int x = 0; x < this.num_ants; x++){
        if(x == 0 && num_iter == 0){
          this.best.set_tour(colony[x].tour);
        }
        else if(colony[x].get_tour_length() < best.get_tour_length()){
          this.best.set_tour(colony[x].tour);
        }
      }

      update_best_found_so_far_pheromone(evaporation_rate);
      num_iter++;
    }
  }


  private void move_aco(Ant[] colony){
    for(int i = 0; i < num_ants; i++){
      colony[i].complete_tour();
      colony[i].evaporate_and_update_pheremone_level(this.epsilon);
    }
  }

  protected void update_best_found_so_far_pheromone(double evaporation_rate){
    double best_length = best.get_tour_length();
    for(Edge e: best.tour){
      double old_p, new_p;
      old_p = e.getPheremone_level();
      new_p = old_p + (evaporation_rate/ best_length);
      e.setPheremone_level(new_p);
    }
  }


  public double get_best_length(){
    return best.get_tour_length();
  }

}
