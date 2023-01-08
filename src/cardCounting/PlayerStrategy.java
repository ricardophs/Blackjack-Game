package cardCounting;

import blackjack.Hand;
import blackjack.PlayerHand;

/** Class that implements a generic Playing Strategy
 */
public abstract class PlayerStrategy {
	/**
	 * Maximum value for the bet. */
	protected int max_bet;
	/**
	 * Minimum possible value for a Double down */
	protected int DDmin;
	/**
	 * Maximum possible value for a Double down */
	protected int DDmax;
	/**
	 * Flag which indicates if it is possible to surrender */
	protected boolean canSurrender;
	/**
	 * Flag which indicates if it possible to double the bet */
	protected boolean canDouble;
	/** 
	 * Flag which indicates if it possible to split */
	protected boolean canSplit;
	/**
	 * Flag which indicates if it possible to insure */
	protected boolean canInsure;
	
	/** Initializes a generic Playing strategy
	 * 
	 * @param max_bet_in Maximum value for the bet
	 * @param DDmin_in Minimum possible value for a Double down.
	 * @param DDmax_in Maximum possible value for a Double down.
	 */
	public PlayerStrategy(int max_bet_in, int DDmin_in, int DDmax_in) {
		max_bet = max_bet_in;
		DDmin = DDmin_in;
		DDmax = DDmax_in;
	}
	
	/** Calculates the next play based on the defined strategy
	 * <p> Each Playing Strategy will implement this method in its own way
	 * 
	 * @param nHands Number of hands that the player has already.
	 * @param p_hand p_hand Player's hand.
	 * @param d_Hand d_hand Dealer's hand.
	 * @param bet Value of the current bet.
	 * 
	 */
	public abstract String getNextPlay(int nHands, PlayerHand p_hand, Hand d_Hand, int bet);
	
}