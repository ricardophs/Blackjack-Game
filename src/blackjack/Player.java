package blackjack;

import java.util.*;

/** Class that implements a Player
 * 
 */
public class Player {
	/**
	 * Array list of hands: the player may have up to 4 hands */	
	protected ArrayList<PlayerHand> hands;
	/**
	 * Player current balance */
	private float balance;
	/**
	 * Player initial bet */
	private int bet;
	/**
	 * Number of hands */
	private int nHands;
	/**
	 * If a player is splitting a hand */
	private boolean isSplitting;
	/**
	 * If an insurance was made */
	private boolean isInsuring;
	
	/** Initializes a new player with an initial bet and balance
	 */
	public Player(int initialBet_in, int balance_in) {
		balance = balance_in;
		bet = initialBet_in;
		nHands = 1;
		hands = new ArrayList<PlayerHand>();
		hands.add(new PlayerHand(bet, true, false));
	}
	
	/** Performs hit action.
	 * 
	 * @param i index of the hand.
	 * 
	 */
	public void hit(int i) {
		hands.get(i).setIsOpening(false);
		hands.get(i).setHitting(true);
	}
	
	/** Adds a card t the hand.
	 * 
	 * @param i Hand's index.
	 * @param card Regular card.
	 * 
	 */
	public void addCard(int i, Card card) {
		hands.get(i).addCard(card);
	}

	/** Gets number of hands.
	 * 
	 * @return nHands Number of hands.
	 * 
	 */
	public int getNHands() {
		return nHands;
	}
	
	/** Performs the stand action.
	 * 
	 * @param i Hand's index.
	 * 
	 */
	public void stand(int i) {
		hands.get(i).setIsOpening(false);
		hands.get(i).setIsStanding(true);
	}
	
	/** Performs the surrender action.
	 * 
	 * @param i Hand's index
	 * 
	 */
	public void surrender(int i) {
		hands.get(i).setIsOpening(false);
		hands.get(i).setIsSurrender();
	}
	
	/** Performs the insurance action
	 */
	public void insurance() {
		hands.get(0).setIsOpening(false);
		isInsuring = true;
		balance -= bet;
	}
	
	/** Checks if the player decided to insure.
	 * 
	 * @return isInsuring Flag which evaluates the decision to ensure.
	 * 
	 */
	public boolean isInsuring() {
		return isInsuring;
	}
	
	/** Performs the split action.
	 * <p> 
	 * Increments the number of hands, removes the previous hand and adds two new ones,
	 * with one card each (only the splitted cards)
	 * 
	 * @param i Index of the card.
	 * 
	 */
	public void split(int i) {
		hands.get(i).setIsOpening(false);
		isSplitting = false;
		nHands++;
		balance -= bet;
		hands.add(i+1, new PlayerHand(bet, false, true));
		hands.get(i+1).addCard(hands.get(i).cards.get(0));
		hands.add(i+2, new PlayerHand(bet, false, true));
		hands.get(i+2).addCard(hands.get(i).cards.get(1));
		hands.remove(i);
	}
	
	/** Peforms the double down action. Also sets isHitting to true since the player can only take one
	 * more card in this hand.
	 * 
	 * @param i Index of the hand.
	 * 
	 */
	public void doubleD(int i) {
		hands.get(i).setIsOpening(false); // Vejam se concordam com esta linha
		balance -= bet;
		hands.get(i).setBet(2*bet);
		hands.get(i).setIsDoubleD();
		hands.get(i).setHitting(true);
	}
	
	/** Places a nwe value for the bet.
	 *  Deduces it's value from the player's balance
	 * 
	 * @param newBet New value for the bet.
	 * 
	 */
	public void placeBet(int newBet) {
		bet = newBet;
		balance -= bet;
		hands.get(0).setBet(newBet);
	}
	
	/** Removes all hands and adds a new empty one
	 */
	public void clearHands() {
		hands.clear();
		nHands = 1;
		isInsuring = false;
		hands.add(new PlayerHand(bet, true, false));
	}

	/** Gets the current value for the balance.
	 * 
	 * @return balance Current value of money possessed by the player.
	 * 
	 */
	public float getBalance() {
		return balance;
	}
	
	/** Updates the balance value 
	 * 
	 * @param update Change in balance
	 */
	public void updateBalance(float update) {
		balance += update;
	}
	
	/** Displays the player's hand
	 * <p> If i is -1 then the player only has one Hand and so the affix
	 * with the hand number won't show up
	 * @param i Hand index
	 */
	public void printPlayersHand(int i){
		String out = new String();
		out += "player's hand ";
		if (i != -1) {
			out += "[" + (i+1) + "] ";
			out += hands.get(i).toString();
		}
		else {
			out += hands.get(0).toString();
		}
		System.out.println(out);	
	}

	/** Checks if the player decided to hit on hand i
	 * 
	 * @param i Hand index
	 * @return Decision to hit
	 */
	public boolean isHittingHand(int i) {
		return hands.get(i).isHitting();
	}

	/** Checks if the player decided to stand
	 * 
	 * @param i Hand index
	 * @return Decision to stand
	 */
	public boolean isStandingHand(int i) {
		return hands.get(i).isStanding();
	}

	/** Checks if the player decided to surrender
	 * 
	 * @param i Hand index
	 * @return Decision to surrender
	 */
	public boolean isSurrendingHand(int i) {
		return hands.get(i).isSurrender();
	}

	/** Checks for a split.
	 * 
	 * @return isSplitting Result of the decision to split.
	 * 
	 */
	public boolean isSplitting() {
		return isSplitting;
	}

	/** Gets value of the bet
	 * 
	 * @return bet Value of the bet
	 */
	public int getBet() {
		return bet;
	}

	/** Sets the decision to split
	 * 
	 * @param b Decision to split
	 */
	public void setIsSplitting(boolean b) {
		this.isSplitting = b;
	}

	/**	Checks for a double down
	 * 
	 * @param i Card's index
	 * @return hands.get(i).isDouble Decision to surrender
	 */
	public boolean isDoubleDHand(int i) {
		return hands.get(i).isDouble();
	}

}
