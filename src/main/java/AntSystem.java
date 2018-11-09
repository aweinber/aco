import java.util.ArrayList;

public class AntSystem {
	TSP problem;
	Ant[] colony;
	int num_ants;
	double alpha;
	double beta;
	Ant best;


	private int max_iterations = 100;
	private double min_percent_above_optimal = 20;

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

	/**
	 * Creates a colony of ants based on the number of ants, alpha
	 * and beta parameters already set.
	 */
	protected void create_colony(){
		for(int i = 0; i < this.num_ants; i++){
			colony[i] = new Ant(this.problem, this.alpha, this.beta);
		}
	}

	/*
		Executes when the program shoudl terminate becasue its reached a max number of iterations.
	 */
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


	/*
	keeps track of whether the function should terminate once its reached a certain percentage from the optimal.
	 */
	private boolean should_terminate_from_close_to_optimal() {
		if (best.tour == null) {
			return false;
		}
		double percentage_above_optimal = ((best.get_tour_length() - problem.optimal) / problem.optimal) * 100.0;
		return percentage_above_optimal < min_percent_above_optimal;
	}


	/**
	 * For every edge in the problem, apply evaporation formula
	 * @param evaporation_rate rate at which evaporation occurs
	 */
	void pheromone_evaporation(double evaporation_rate){
		for(Edge e: problem.get_edges()){
			double old_p, new_p;
			old_p = e.getPheremone_level();
			new_p = old_p * (1 - evaporation_rate);
			e.setPheremone_level(new_p);

		}
	}




}
