package blackjack;


/** Class destined to saving the player statics regarding several games.
 * <p> Extends the Stats class, as it also implements some other metrics such as 
 * Win/Lose/Push ratios and balance variation from the initial balance. 
 * <p> These are the metrics specific to the player, while the ones on the superclass are common 
 * to both the dealer and the player.
 * 
*/
public class PlayerStats extends Stats {
	private int wins;
	private int losses;
	private int pushes;
	private int initBalance;

	/** Initializes a new Player Statistics
	 * 
	 * @param init_balance Stores the value for the initial balance of the player, for later comparison
	 * 
	*/
	public PlayerStats(int init_balance) {
		super();
		initBalance = init_balance;
		wins = 0;
		losses = 0;
		pushes = 0;
	}
	
	/** Increments the win, loss and push counts, according to a flag.
	 * 
	 * @param r flag that signals the game result: 
	 * <p> 1 - player wins
	 * <p> 0 - player pushes
	 * <p> -1 - player loses
	 * 
	*/
	public void incWLP(int r) {
		if(r == 1) ++wins;
		if(r == -1) ++losses;
		if(r == 0) ++pushes;
	}

	/** Returns one of the metrics computed, according to a flag
	 * 
	* @param r flag that signals the game result: 
	 * <p> 1 - player winning rate
	 * <p> 0 - player pushing rate
	 * <p> -1 - player losing rate
	 * 
	*/
	public double getWLPavg(int r) {
		if(r == 1) return (double)wins/handsPlayed;
		if(r == -1) return (double)losses/handsPlayed;
		if(r == 0) return (double)pushes/handsPlayed;
		return 0;
	}
	
	/** Calculates the percentage gain based on the initial balance and current balance.
	 * 
	 * @param cur_balance Current balance which indicates the available money at the moment.
	 * 
	*/
	public double percentageOfGain(double cur_balance) {
		return cur_balance/initBalance;
	}

}