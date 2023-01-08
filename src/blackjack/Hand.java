package blackjack;

import java.util.*;


/** Class that implements a general hand
 * 
*/
public class Hand {
	/**
	 * Cards composing the hand
	 */
	protected ArrayList<Card> cards;
	/**
	 * total hand value
	 */
	protected int value;
	/**
	 * number of cards in the hand
	 */
	protected int nCards;
	/**
	 * flag that signals if an hand has busted (true)
	 */
	protected boolean isBust;
	/**
	 * flag that signals if an hand stands (true)
	 */
	protected boolean isStanding;
	
	/** Creates an empty hand of cards.
	 */
	public Hand() {
		cards = new ArrayList<Card>();
		value = 0;
		nCards = 0;
		isBust = false;
		isStanding = false;
	}
	
	/** Checks if the hand busts.
	 * 
	 * @return A boolean that determines if the hand busted.
	 * 
	*/
	public boolean isBust() {
		return isBust;
	}
	
	/** Sets the isStanding variable.
	 * 
	*/
	public void setIsStanding(boolean bool) {
		isStanding = bool;
	}
	
	/** Checks if the hand stands.
	 * 
	 * @return A boolean that determines if the player decided to stand.
	 * 
	*/
	public boolean isStanding() {
		return isStanding;
	}
	
	/** Checks for a BlackJack.
	 * 
	 * @return A boolean that classifies the BlackJack.
	 * 
	*/
	public boolean checkBlackjack() {
		return (nCards == 2 && value == 21);
	}
	
	/** Gets Card value.
	 * 
	 * @return Value of the card.
	 * 
	*/
	public int getValue() {
		return value;
	}
	
	/**
	 * Gets all the visible cards in the hand 
	 * @return visible cards in an ArrayList of Cards
	 */
	public ArrayList<Card> getCards(){
		ArrayList<Card> visible_cards = new ArrayList<Card>();
		for(Card c : cards)
			if(c.getIsUp())
				visible_cards.add(c);
		return visible_cards;
	}
	
	/** Gets number of cards.
	 * 
	 * @return Number of cards.
	 * 
	*/
	public int getNCards() {
		return nCards;
	}
	
	/** Adds a card to the hand.
	 * <p>
	 * Also computes the new value of the hand, if the hand has busted
	 * and if any Ace is downgraded to hard.
	 * @param card card to add.
	 * 
	*/
	public void addCard(Card card) {
		cards.add(card);
		if (nCards != 1)
			cards.get(nCards).setIsUp(true);
		++nCards;
		value += card.getIntValue();
		if(value > 21) {
			for(Card c : cards) {
				if(c.getValue().equals("A") && c.isSoft()) {
					value -= 10;
					c.setSoft(false);
					if(value <= 21) break;
				}
			}
		}
		if (value > 21) {
			isBust = true;
		}
	}
	
	/** Resets the hand.
	*/
	public void reset() {
		cards.clear();
		value = 0;
		nCards = 0;
		isBust = false;
		isStanding = false;
	}
	
	/** Gets first card 
	 * 
	 * @return First card of the hand
	 * 
	 */
	public Card getFirst() {
		return cards.get(0);
	}
	
	/** Converts to String
	 * 
	 */
	@Override
	public String toString() {
		String out = new String();
		for (int i = 0; i < cards.size(); i++) {
			out += cards.get(i).toString();
			out += " ";
		}
		if (cards.get(1).getIsUp() == true)
			out += "(" + value + ")";
		return out;
	}

}

