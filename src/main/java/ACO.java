import java.util.*;

public class ACO {
  TSP problem;
  Ant[] colony;
  int num_ants;
  double alpha;
  double beta;
  double evaporation_rate;
  Ant best;
  double epsilon;


  int max_iterations = 100;


  double percentage_from_optimal = 20;

  private static final int TERMINATE_AT_NUM_ITER = 1;
  private static final int TERMINATE_AT_PERCENTAGE_FROM_OPTIMAL = 2;
  private static final int TERMINATE_AT_FIRST_CONDITION = 3;

  private int termination_condition;


  ACO(TSP problem, int num_ants, int max_iterations, double alpha, double beta, double evaporation_rate, double epsilon, int termination_condition){
    colony = new Ant[num_ants];
    this.problem  = problem;
    this.num_ants = num_ants;
    create_colony();
    this.max_iterations = max_iterations;
    this.alpha = alpha;
    this.beta = beta;
    this.best = new Ant(this.problem, this.alpha, this.beta);
    this.evaporation_rate = evaporation_rate;
    this.termination_condition = termination_condition;
    this.epsilon = epsilon;
  };

  public void execute_aco() {

    int num_iter = 0;

    while (!should_terminate(num_iter)) {
      
      move(colony);
      pheremone_evaporation(evaporation_rate);

      for(int x = 0; x < this.num_ants; x++){
        if(x == 0 && num_iter == 0){
          this.best.set_tour(colony[x].tour);
        }
        else if(colony[x].get_tour_length() < best.get_tour_length()){
          this.best.set_tour(colony[x].tour);
        }
      }

      update_best_found_so_far_phermone(evaporation_rate);

      num_iter++;
     
    }
    System.out.println("Terminating at number iteration " + num_iter);
  }

  private boolean should_terminate(int num_iter) {
    if (termination_condition == TERMINATE_AT_FIRST_CONDITION) {
      return check_num_iterations(num_iter) || check_percentage_from_optimal();
    }
    else if (termination_condition == TERMINATE_AT_PERCENTAGE_FROM_OPTIMAL) {
      return check_percentage_from_optimal();
    }
    else if (termination_condition == TERMINATE_AT_NUM_ITER) {
      return check_num_iterations(num_iter);
    }
    return false;
  }

  private boolean check_num_iterations(int num_iter) {
    return num_iter >= max_iterations;
  }

  private boolean check_percentage_from_optimal() {
    return ((best.get_tour_length() - problem.optimal / problem.optimal) * 100) < percentage_from_optimal;
  }

  private void create_colony(){
    for(int i = 0; i < this.num_ants; i++){
      colony[i] = new Ant(this.problem, this.alpha, this.beta);

    }
  }

  private void move(Ant[] colony){
    for(int i = 0; i < num_ants; i++){
      colony[i].complete_tour();
      colony[i].evaporate_and_update_pheremone_level(this.epsilon);
    }
  }

  private void pheremone_evaporation(double evaporation_rate){
    ArrayList<Edge> edges = new ArrayList<Edge>(problem.get_edges());
    for(Edge e: edges){
      double old_p, new_p;
      old_p = e.getPheremone_level();
      new_p = old_p * (1 - evaporation_rate);
      e.setPheremone_level(new_p);
      //System.out.println(new_p);
    }
  }

  private void update_best_found_so_far_phermone(double evaporation_rate){
    for(Edge e: best.tour){
      double old_p, new_p;
      old_p = e.getPheremone_level();
      new_p = old_p + evaporation_rate/best.get_tour_length();;
      e.setPheremone_level(new_p);
    }
  }


  public double get_best_length(){
    return best.get_tour_length();
  }

}
