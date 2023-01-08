package cardCounting;

import blackjack.Card;

/** Ace 5 betting strategy.
 * <p> Extends the betting strategy superclass
 * 
*/
public class Ace5 extends BettingStrategy {
	
	/** Initializes the Ace5 card counting strategy.
	 * 
	 * @param min_bet_in Represents the minimum value for the bet.
	 * @param max_bet_in Represents the maximum value for the bet.
	 * <p>
	 * (same parameters as the super class, BettingStrategy)
	*/
	public Ace5(int min_bet_in, int max_bet_in) {
		super(min_bet_in, max_bet_in);
	}
		
	/** Updates the counting variable based on a card
	 * <p> If the card is an Ace, subtract one to the count;
	 * <p> If the card is a Five, add one to the count.
	 * 
	 * @param card Represents the card.
	 * 
	*/
	public void updateCount(Card card) {
		if (card.getValue().equals("A"))
			count--;
		else if (card.getValue().equals("5"))
			count++;
	}
	
	/** Calculates the next bet that should be made based on the count.
	 * 
	 * If the count is greater or equal to 2, then the bet is doubled (up to the maximum bet).
	 * Otherwise, the bet is set its minimum value
	 *
	*/
	public int getNextBet() {
		if (count >= 2)
			return (2*curr_bet > max_bet) ? max_bet : 2*curr_bet;
		else
			return min_bet;
	}
	
}
