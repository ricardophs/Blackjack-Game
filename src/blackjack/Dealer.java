package blackjack;

/** Class that implements a blackjack Dealer.
 * 	<p> The dealer is also responsible for creating the game shoe. 
 * 
 * @author Ricardo Santos 90178
 * @author Tomás Bessa 90200
 * @author Inês Ferreira 90395
 * 
*/
public class Dealer {
	/**
	 * Game shoe
	 */
	protected Shoe shoe;
	/**
	 * Dealer's current playing hand
	 */
	protected Hand hand;
		
	/** Creates a new Dealer with a random shoe and an empty hand.
	 * @param nDecks_in Number of decks that compose the shoe.
	*/
	public Dealer(int nDecks_in) {
		shoe = new Shoe(nDecks_in);
		hand = new Hand();
	}
	
	/** Creates a new Dealer with a shoe read from a file and an empty hand.
	 * @param shoeFile_in shoe file from which the shoe will be created.
	*/
	public Dealer(String shoeFile_in) {
		shoe = new Shoe(shoeFile_in);
		hand = new Hand();
	}

	/** Adds a card to the dealer's hand.
	 * @param card Represents a regular card.
	*/
	public void addCard(Card card) {
		hand.addCard(card);
	}
	
	/** Shuffles the shoe.
	*/
	public void shuffle() {
		shoe.shuffle();
	}
			
	/** Deals a card from the shoe
	*/
	public Card dealCards() {
		return shoe.getCard();
	}
	
	/** Prints the dealer's hand
	*/
	public void printDealersHand(){
		String out = new String();
		out += "dealer's hand ";
		out += hand.toString();
		System.out.println(out);	
	}
	
	/** Sets the dealer's hand to a visible status
	*/
	public void setVisible() {
		hand.cards.get(1).setIsUp(true);
	}
	
	/** Clears the hand of the dealer.
	*/
	public void clearHand() {
		hand.reset();
	}
		
}
