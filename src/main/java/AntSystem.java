import java.util.ArrayList;

public class AntSystem {
	TSP problem;
	Ant[] colony;
	int num_ants;
	double alpha;
	double beta;
	Ant best;


	int max_iterations = 100;


	double percentage_from_optimal = 20;

	private static final int TERMINATE_AT_NUM_ITER = 1;
	private static final int TERMINATE_AT_PERCENTAGE_FROM_OPTIMAL = 2;
	private static final int TERMINATE_AT_FIRST_CONDITION = 3;

	private int termination_condition;

	public AntSystem(TSP problem, int num_ants, double alpha, double beta, int max_iterations, int termination_condition) {
		colony = new Ant[num_ants];
		this.problem = problem;
		this.num_ants = num_ants;
		this.alpha = alpha;
		this.beta = beta;
		this.max_iterations = max_iterations;
		this.termination_condition = termination_condition;
	}

	protected void create_colony(){
		for(int i = 0; i < this.num_ants; i++){
			colony[i] = new Ant(this.problem, this.alpha, this.beta);
		}
	}

	protected boolean should_terminate(int num_iter) {
		if (termination_condition == TERMINATE_AT_FIRST_CONDITION) {
			return should_terminate_from_max_iterations(num_iter) || should_terminate_from_close_to_optimal();
		}
		else if (termination_condition == TERMINATE_AT_PERCENTAGE_FROM_OPTIMAL) {
			return should_terminate_from_close_to_optimal();
		}
		else if (termination_condition == TERMINATE_AT_NUM_ITER) {
			return should_terminate_from_max_iterations(num_iter);
		}
		return false;
	}

	private boolean should_terminate_from_max_iterations(int num_iter) {
		return num_iter >= max_iterations;
	}

	private boolean should_terminate_from_close_to_optimal() {
		return ((best.get_tour_length() - problem.optimal / problem.optimal) * 100) < percentage_from_optimal;
	}


	protected void pheremone_evaporation(double evaporation_rate){
		ArrayList<Edge> edges = new ArrayList<Edge>(problem.get_edges());
		for(Edge e: edges){
			double old_p, new_p;
			old_p = e.getPheremone_level();
			new_p = old_p * (1 - evaporation_rate);
			e.setPheremone_level(new_p);

		}
	}



}
