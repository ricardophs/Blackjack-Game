package cardCounting;

import blackjack.Card;

/** Standard betting strategy
 * <p> Simulates a more natural way of betting, increasing the bet when winning, decreasing it when losing.
 * <p> Extends the betting strategy superclass
 *
 */
public class StandardStrategy extends BettingStrategy {
	/**
	 * Saves the bet to be made next
	 */
	int next_bet;
	
	/** 
	 * Initialized in the same way as its superclass, BettingStrategy, but defaulting the next bet to the minimum bet value.
	 */
	public StandardStrategy(int min_bet_in, int max_bet_in) {
		super(min_bet_in, max_bet_in);
		next_bet = min_bet_in;
	}

	/** Gets the next value for the bet
	 */
	public int getNextBet() {
		return next_bet;
	}
	
	/** Updates the value for the bet<p>
	 * If the player won the previous round (result = 1), increment the bet by min_bet up to a bet
	 * value of max_bet; <p>
	 * If the player lost the previous round (result = -1), decrement the bet by min_bet, up to a
	 * bet value of min bet; <p>
	 * If the player pushed the previous round (result = 0), the bet remains the same.
	 *<p>
	 * Sets curr_bet to next_bet in order to be able to perform multiple updates followed by one another,
	 * as needed when, for example, the player splits and has more than one hand.
	 * @param result: outcome of the last round
	 */
	public void updateBet(int result) {
		if(result == 1)
			next_bet = (curr_bet + min_bet <= max_bet) ? curr_bet + min_bet : max_bet;
		else if (result == -1)
			next_bet = (curr_bet - min_bet >= min_bet) ? curr_bet - min_bet : min_bet;
		else
			next_bet = curr_bet;
		curr_bet = next_bet;
	}

	/** 
	 * This betting strategy doens't implement a count, since the bet is determined only by the last round's outcome
	 * (it has no memory).
	 */
	@Override
	public void updateCount(Card c) {	
		return;
	}
		
}