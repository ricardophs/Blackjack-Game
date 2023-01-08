package blackjack;

/** Class relative to a usable hand by a player.
 * <p>
 * Extends a regular Hand
 * 
*/
public class PlayerHand extends Hand {
	/** current bet associated with the hand */	
	private int bet;
	/** if a hand is an opening hand (hand just dealt) */	
	private boolean isOpening;
	/** if a hand is a pair */	
	private boolean isPair;
	/** if a hand was doubled */	
	private boolean isDoubleD;
	/** if a hand was splitted */	
	private boolean isSplit;
	/** if a hand was surrendered */	
	private boolean isSurrender;
	/** if a hand is still hitting */	
	private boolean isHitting;
	
	/** Creates a usable Hand for the player.
	 * 
	 * @param bet_in Bet that is placed at the beginning.
	 * @param opening if it is an opening hand
	 * @param split Checks for a split.
	 * 
	*/
	public PlayerHand(int bet_in, boolean opening, boolean split) {
		super();
		bet = bet_in;
		isOpening = opening;
		isSplit = split;
		isDoubleD = false;
		isPair = false;
		isSurrender = false;
	}
	
	/** Gets the value of the bet.
	 * 
	 * @return bet
	 * 
	*/
	public int getBet() {
		return bet;
	}
	
	/** Doubles the current bet
	*/
	public void doubleBet() {
		bet *= 2;
	}
	
	/** Flag which indicates that the player decided to surrender.
	 * 
	 * @return isSurrender Decision to surrender.
	 * 
	*/
	public boolean isSurrender() {
		return isSurrender;
	}
	
	/** Activates if the player decides to surrender.
	 * 
	*/
	public void setIsSurrender() {
		isSurrender = true;
	}
	
	/** Flag which indicates if the player's hand is an opening hand or not.
	 * 
	 * @return isOpening
	 * 
	*/
	public boolean isOpening() {
		return isOpening;
	}
	
	/** Activates if the hand is open.
	*/
	public void setIsOpening(boolean bool) {
		isOpening = bool;
	}
	
	/** Checks if the hand is a pair.
	 * 
	 * @return isPair Flag which indicates if the cards have the same value.
	 * 
	*/
	public boolean isPair() {
		return isPair;	
	}
	
	/** Checks if there was a double down.
	*/
	public boolean isDouble() {
		return isDoubleD;
	}
	
	/** Checks if a double down was made.
	 *
	*/
	public void setIsDoubleD() {
		isDoubleD = true;
	}
	
	/** Checks if there was a split.
	 * 
	 * @return isSplit Flag for a split.
	 * 
	*/
	public boolean isSplit() {
		return isSplit;
	}
	
	/** Sets the bet the player wants to make.
	 * 
	 * @param bet_in bet value to be set.
	 * 
	*/
	public void setBet(int bet_in) {
		bet = bet_in;
	}
	
	/** Adds a card to the player's hand and sets flags accordingly
	 * <p>
	 * Extends the addCard method from a regular hand
	 * 
	 * @param card Represents a regular card.
	 * 
	*/
	public void addCard(Card card) {
		super.addCard(card);
		card.setIsUp(true);
		isPair = false;
		if (nCards == 2) {
			if (cards.get(0).equals(cards.get(1)))
				isPair = true;			
		}
	}
	
	/** Converts to string
	 */
	@Override
	public String toString() {
		String out = new String();
		for (int i = 0; i < cards.size(); i++) {
			out += cards.get(i).toString();
			out += " ";
		}
		out += "(" + value + ")";
		return out;
	}

	
	/** Checks if the player is hitting
	 * 
	 * @return isHitting Flag which indicates if an hit is being made.
	 * 
	*/
	public boolean isHitting() {
		return isHitting;
	}
	
	/** Sets the hitting state
	 * 
	 * @param isHitting Flag which indicates if an hit is being made.
	 * 
	*/
	public void setHitting(boolean isHitting) {
		this.isHitting = isHitting;
	}
	
}
