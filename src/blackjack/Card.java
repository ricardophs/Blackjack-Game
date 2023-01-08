package blackjack;

/** 
 * Class that implements a game card
 * 
 * @author Ricardo Santos 90178
 * @author Tomá Bessa 90200
 * @author Inês Ferreira 90395
 * 
*/
public class Card {
	/**
	* The String value of the card:
	* <p>
	* A for an Ace; 
	* 2 to 10 for the cards with that value; 
	* J, Q and K to Jack, Queen and King, respectively.
	*/
	private String value;
	/**
	 * The suit of the card: <p>
	 * D: Diamonds; C: Clubs; S: Spades; H: Hearts.
	*/
	private char suit;
	/**
	 * Numeric value of a card:
	*/
	private int intValue;
	/**
	 * Visibility of a card: {@code true} if the card is visible, {@code false} otherwise
	*/
	private boolean isUp;
	/**
	 * If an Ace counts as a 11 ({@code true}) or a 1 ({@code false})
	*/
	private boolean isSoft;
	
	/** Used to create a card from an integer.
	 * @param n integer from 0 to 51 
	*/
	public Card(int n) {
		
		isUp = false;
		
		// Variable that determines selection of the suit
		int suit_sel = n/13 + 1;
		
		//	- suit_sel = 1 -> Spades / suit_sel = 2 -> Hearts
		//	- suit_sel = 3 -> Clubs  / suit_sel = 4 -> Diamonds
				
		// Variable that determines the value of each card
		intValue = n%13 + 1;
		if (intValue > 10) {
			
			if (intValue == 11) 
				value = "J";
			
			if (intValue == 12) 
				value = "Q";
			
			if (intValue == 13) 
				value = "K";
		
			intValue = 10;
		}
		
		else
			value = Integer.toString(intValue);
		
		if (value.equals("1")) {
			value = "A";
			intValue = 11;
		}
		
		// Covers all cards that have the same value
		
		switch(suit_sel) {
			
			case 1:
				suit = 'S';
				break;

			case 2:
				suit = 'H';
				break;
				
			case 3:
				suit = 'C';
				break;
				
			default:
				suit = 'D';
		}

		isSoft = (value.equals("A"));
	}
	
	/** Creates a specific card.
	 * 
	 * @param value_in card String value.
	 * @param suit_in suit of the card.
	 * 
	*/
	public Card(String value_in, char suit_in) {
		isUp = false;
		isSoft = value_in.equals("A");
		value = value_in;
		suit = suit_in;
		if (value_in.equals("J") || value_in.equals("Q") || value_in.equals("K"))
			intValue = 10;
		else if (value_in.equals("A"))
			intValue = 11;
		else
			intValue = Integer.parseInt(value_in);
	}
	
	/** Gets the value of the card.
	 * 
	 * @return value Value of the card
	 * 
	*/
	public String getValue() {
		return value;
	}
	
	/** Gets the suit of the card.
	 * 
	 * @return suit Suit of the card.
	 * 
	*/
	public char getSuit() {
		return suit;
	}
	
	/** Gets the numeric value of a card.
	 *  
	 * @return intValue numeric value.
	 * 
	*/
	public int getIntValue() {
		return intValue;
	}
	
	/** Gets the variable which determines if the card is face up. 
	 * 
	 * @return isUp Evaluates if the card is turned up and therefore visible.
	 * 
	*/
	public boolean getIsUp() {
		return isUp;
	}
	
	/** Sets a flag isSoft.
	 * 
	 * @param flag set value.
	 * 
	*/
	public void setSoft(boolean flag) {
		isSoft = flag;
	}
	
	/** Gets the variable isSoft, which determines the value of the Ace.
	 * 
	 * @return isSoft true if the Ace is worth 11, false if it's worth 1.
	 * 
	*/
	public boolean isSoft() {
		return isSoft;
	} 
	
	/** Sets the variable isUp.
	 * @param visibility setting value.
	 * 
	*/
	public void setIsUp(boolean visibility) {
		isUp = visibility;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + intValue;
		result = prime * result + (isSoft ? 1231 : 1237);
		result = prime * result + (isUp ? 1231 : 1237);
		result = prime * result + suit;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (value.equals("A") && other.value.equals("A"))
			return true;
		if (intValue == other.intValue)
			return true;
		return false;
	}

	/** Turns the result into a string
	 */
	@Override
	public String toString() {
		if (isUp == true)
			return value + "" + suit;
		else return "X";
	}
	
}