import java.util.*;

public class EAS extends AntSystem{

  private double evaporation_rate;
  private Ant best;
  private double elitism;

  int max_iterations = 100;

  double percentage_from_optimal = 20;

  private static final int TERMINATE_AT_NUM_ITER = 1;
  private static final int TERMINATE_AT_PERCENTAGE_FROM_OPTIMAL = 2;
  private static final int TERMINATE_AT_FIRST_CONDITION = 3;

  private int termination_condition;



  EAS(TSP problem, int num_ants, int max_iterations, double alpha, double beta, double evaporation_rate, double elitism, int termination_condition){
    super(problem, num_ants, alpha, beta, max_iterations, termination_condition);
    super.create_colony();
    this.best = new Ant(this.problem, this.alpha, this.beta);
    this.elitism = elitism;
    this.evaporation_rate = evaporation_rate;
    this.termination_condition = termination_condition;

  }

  public void execute_eas() {

    int num_iter = 0;
    while (!super.should_terminate(num_iter)) {

      move_eas(colony);
      pheremone_evaporation(evaporation_rate);

      for(int x = 0; x < this.num_ants; x++){
        if(x == 0 && num_iter == 0){
          System.out.println("setting best tour");
          this.best.set_tour(colony[x].tour);
        }
        else if(colony[x].get_tour_length() < best.get_tour_length()){
          this.best.set_tour(colony[x].tour);
        }
        colony[x].update_pheremone_level();
      }

      update_best_found_so_far_pheromone(elitism);
      num_iter++;
    }
    System.out.println("Terminating at number iteration " + num_iter);
  }


  private void move_eas(Ant[] colony){
    for(int i = 0; i < num_ants; i++){
      colony[i].complete_tour();
    }
  }

  protected void update_best_found_so_far_pheromone(double elitism){
    double best_length = best.get_tour_length();
    for(Edge e: best.tour){
      double old_p, new_p;
      old_p = e.getPheremone_level();
      new_p = old_p + (elitism / best_length);
      e.setPheremone_level(new_p);
    }
  }


  public double get_best_length(){
    return best.get_tour_length();
  }

}

