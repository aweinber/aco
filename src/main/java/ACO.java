import java.util.*;

public class ACO {
  TSP problem;
  Ant[] colony;
  int num_ants;
  int num_iter;
  double phi;
  double beta;
  double evaporation_rate;
  Ant bfsf;

  ACO(TSP problem, int num_ants, int num_iter, double phi, double beta, double evaporation_rate){
    colony = new Ant[num_ants];
    this.problem  = problem;
    this.num_ants = num_ants;
    create_colony(num_ants, problem);
    this.num_iter = num_iter;
    this.phi = phi;
    this.beta = beta;
    this.bfsf = new Ant(problem);
    this.evaporation_rate = evaporation_rate;

  };

  public void execute_aco() {


    for(int i = 0; i < num_iter; i++) {
      
      move(colony);
      pheremone_evaporation(evaporation_rate);

      for(int x = 0; x < this.num_ants; x++){
        if(x == 0 && i == 0){
          this.bfsf.set_tour(colony[x].tour);
        }
        else if(colony[x].get_tour_length() < bfsf.get_tour_length()){
          this.bfsf.set_tour(colony[x].tour);
        }
      }

      System.out.println("Best tour so far: " + this.bfsf.get_tour_length());
      update_best_found_so_far_phermone(evaporation_rate);
     
    }
  }

  private void create_colony(int num_ants, TSP problem){
    for(int i = 0; i < num_ants; i++){
      colony[i] = new Ant(problem);
    }
  }

  private void move(Ant[] colony){
    for(int i = 0; i < num_ants; i++){
      colony[i].complete_tour();
    }
  }

  private void pheremone_evaporation(double evaporation_rate){
    ArrayList<Edge> edges = new ArrayList<Edge>(problem.get_edges());
    for(Edge e: edges){
      double old_p, new_p;
      old_p = e.getPheremone_level();
      new_p = old_p * (1 - evaporation_rate);
      e.setPheremone_level(new_p);
    }
  }

  private void update_best_found_so_far_phermone(double evaporation_rate){
    for(Edge e: bfsf.tour){
      double old_p, new_p;
      old_p = e.getPheremone_level();
      new_p = old_p + evaporation_rate/bfsf.get_tour_length();;
      e.setPheremone_level(new_p);
    }
  }


  public double get_bfsf_length(){
    return bfsf.get_tour_length();
  }

}
