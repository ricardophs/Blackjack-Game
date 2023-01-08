package cardCounting;

import blackjack.Card;

/** Class that implements a generic betting Strategy
 */

public abstract class BettingStrategy {
	
	/**
	 * count according to which the betting decisions will be made (most usefull for Ace 5 or variations)
	 */
	protected int count;
	/**
	 * Bet at the current playing round
	 */
	protected int curr_bet;
	/**
	 * Overall minimum bet allowed
	 */
	protected int min_bet;
	/**
	 * Overall maximum bet allowed
	 */
	protected int max_bet;
	
	/** Initializes the bet parameters. Sets count to 0, makes current bet as the default, minimum bet 
	 * (will be changed later) and sets the min and max bets
	 * 
	 * @param min_bet_in Minimum value for the bet.
	 * @param max_bet_in Maximum value for the bet.
	 * 
	 */
	public BettingStrategy(int min_bet_in, int max_bet_in) {
		curr_bet = min_bet_in;
		min_bet = min_bet_in;
		max_bet = max_bet_in;
		count = 0;
	}
	
	/** Updates the value for the bet.
	 * 
	 * @param bet_in Value of the bet.
	 * 
	 */
	public void setBet(int bet_in) {
		curr_bet = bet_in;
	}
	
	/** The way the next bet is computed is dependent on the chosen betting strategy
	 */
	public abstract int getNextBet();
	
	/** The way the count is updated is dependent on the chosen betting strategy
	 */
	public abstract void updateCount(Card card);
	
	/** Resets the auxiliary counter (useful when there's a shoe shuffle)
	 */
	public void resetCount() {
		count = 0;
	}
	
}