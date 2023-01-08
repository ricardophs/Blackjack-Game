package cardCounting;

import blackjack.Card;
import blackjack.Hand;
import blackjack.PlayerHand;

/**
 * Class that implements the Hi-Lo card counting strategy 
 */
public class HiLo extends PlayerStrategy {

	/**
	 * Running counter which updates based on the value of the cards that are dealt */
	private int running_count;
	/**
	 * Result of the division of the running count and the remaining number of decks */
	private float true_count;
	/**
	 * Number of cards that were already dealt */
	private int dealt_cards;
	/**
	 * Number of decks that compose the shoe */
	private int nDecks;
	/**
	 * Apply basic strategy for the cases that the Hi-Lo Strategy doens't offer an answer
	 */
	private PlayerStrategy basic_strat;
	
	/** 
	 * Implements the Hi-Lo counting strategy
	 * 
	 * @param max_bet_in Maximum value that can be assigned to a bet
	 * @param DDmin_in Minimum value for which a player can double down on their bet
	 * @param DDmax_in Maximum value for which a player can double down on their bet
	 * @param nDecks_in Number of decks that constitute the shoe
	 */
	public HiLo (int max_bet_in, int DDmin_in, int DDmax_in, int nDecks_in) {
		super(max_bet_in, DDmin_in, DDmax_in);
		running_count = 0;
		true_count = 0;
		dealt_cards = 0;
		nDecks = nDecks_in;
		basic_strat = new Basic(max_bet, DDmin, DDmax);
	}
	
	/** Updates the auxiliary variable count, according to the predefined table of values of the Hi-Lo 
	 * Strategy: <p>
	 * if the card is an Ace or has a numeric value of 10, decrement the running counter by one unit;
	 * <p>
	 * if the card has a numeric value smaller than or equal to 6, increment the counter by one unit;
	 * <p>
	 * otherwise, the counter remains the same.
	 * <p>
	 * The running counter is then divided by the  number of decks still left to play in order to get the true counter. 
	 * This will be the one used to make the Hi-Lo decisions
	 * @param card based on which the update will be made
	 * 
	*/
	public void updateCounts(Card card) {
		dealt_cards++;
		if (card.getIntValue() >= 2 && card.getIntValue() <= 6)
			running_count++;
		else if (card.getIntValue() >= 10 || card.getIntValue() == 1)
			running_count--;
		int decks_left = (int) Math.round((52*nDecks-dealt_cards)/52.0);
		if(decks_left <= 0) decks_left = 1;
		true_count = Math.round((float)running_count/decks_left);
	}
	
	/** Gets next advisable play, according to the Hi-Lo Strategy's Illustrious 18 and Fab 4 variations,
	 *  complemented with the basic Strategy.
	 * 
	 * @param nHands Number of hands
	 * @param p_hand Player's hand
	 * @param d_hand Dealer's hand
	 * @param bet Value of the bet
	 * 
	 */
	public String getNextPlay(int nHands, PlayerHand p_hand, Hand d_hand, int bet) {
		canSurrender = true;
		canDouble = ((p_hand.getValue() >= DDmin && p_hand.getValue() <= DDmax) && (2*bet <= max_bet));
		if(p_hand.getNCards() != 2 ) {
			canSurrender = false;
			canDouble = false;
		}
		canSplit = (nHands < 4);
		canInsure = (p_hand.isOpening());
		int p_hand_value = p_hand.getValue();
		int d_card_value = d_hand.getFirst().getIntValue();
		if(d_hand.getFirst().getValue().equals("A")) 
			if(true_count >= 3 && canInsure) 
				return "i";
		if(p_hand_value == 9) {
			if(d_card_value == 2) {
				if (true_count >= 1 && canDouble)
					return "2";
				else 
					return "h";
			}
			else if(d_card_value == 7) {
				if (true_count >= 3 && canDouble)
					return "2";
				else 
					return "h";
			}
			else return basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
		}
		else if(p_hand_value == 10) {
			if(d_card_value == 10 || d_card_value == 11) {
				if (true_count >= 4 && canDouble)
					return "2";
				else 
					return "h";
			}
			else return basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
		}
		else if(p_hand_value == 11) {
			if(d_card_value == 11) {
				if (true_count >= 1 && canDouble)
					return "2";
				else 
					return "h";
			}
			else return basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
		}
		else if(p_hand_value == 12) {
			if(p_hand.isPair() && p_hand.getCards().get(0).getValue().equals("A") && !p_hand.isOpening())
				return basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
			if(d_card_value == 2)
				return (true_count >= 3) ? "s" : "h";
			else if(d_card_value == 3)
				return (true_count >= 2) ? "s" : "h";
			else if(d_card_value == 4)
				return (true_count >= 0) ? "s" : "h";
			else if(d_card_value == 5)
				return (true_count >= -2) ? "s" : "h";
			else if(d_card_value == 6)
				return (true_count >= -1) ? "s" : "h";
			else return basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
		} 
		else if(p_hand_value == 13) {
			if(d_card_value == 2)
				return (true_count >= -1) ? "s" : "h";
			if(d_card_value == 3)
				return (true_count >= -2) ? "s" : "h";
			else return basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
		} // Fab 4
		else if(p_hand_value == 14) {
			if(d_card_value == 10)
				return (true_count >= 3 && canSurrender) ? "u" : basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
			else return basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
		}
		else if(p_hand_value == 15) {
			if(d_card_value == 9)
				return (true_count >= 2 && canSurrender) ? "u" : basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
			else if(d_card_value == 10) { // Fab 4 + Illustrious 18
				if(true_count >= 4)
					return "s";
				else return (true_count > 0 && canSurrender) ? "u" : "h";
			}
			else if(d_card_value == 11)
				return (true_count >= 1 && canSurrender) ? "u" : basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
			else return basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
		} // end of Fab 4
		else if(p_hand_value == 16) {
			if(d_card_value == 9)
				return (true_count >= 5) ? "s" : "h";
			else if(d_card_value == 10)
				return (true_count >= 0) ? "s" : "h";
			else return basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
		} 
		else if(p_hand.isPair() && p_hand_value == 20) {
			if(d_card_value == 5)
				return (canSplit && true_count >= 5) ? "p" : "s";
			else if(d_card_value == 5)
				return (canSplit && true_count >= 4) ? "p" : "s";
			else return basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);
		}
		else return basic_strat.getNextPlay(nHands, p_hand, d_hand, bet);	
	}
	
	/** Resets the auxiliary counters
	*/
	public void resetCounts() {
		dealt_cards = 0;
		running_count = 0;
		true_count = 0;
	}
	
}