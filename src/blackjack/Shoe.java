package blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/** 
 * Class that implements a game shoe
 * 
 * @author Ricardo Santos 90178
 * @author Tomás Bessa 90200
 * @author Inês Ferreira 90395
 * 
*/
public class Shoe {
	/**
	 * The shoe is an array of cards
	 */
	private ArrayList<Card> shoe;
	/**
	 * Number of decks in the shoe
	 */
	private int nDecks;
	/**
	 * Number of cards already dealt since the last shuffle
	 */
	private int nDealtCards;
	
	/** Creates a default shoe (unshuffled)
	 * 
	 * @param nDecks_in Number of decks that form a shoe.
	 * 
	 */
	public Shoe(int nDecks_in) {
		nDecks = nDecks_in;
		nDealtCards = 0;
		shoe = new ArrayList<Card>();
		for (int j = 0; j < nDecks; j++) {
			for (int i = 0; i < 52; i++)
				shoe.add(new Card(i));
		}
	}
	
	/** Creates a shoe based on the file input
	 * <p>
	 * Exits if an error is detected
	 * 
	 * @param shoeFile_in Name of the file which contains the shoe
	 * 
	 */
	public Shoe(String shoeFile_in) {
		nDealtCards = 0;
		shoe = new ArrayList<Card>();
		ArrayList<String> cards = new ArrayList<String>();		
		Scanner scanner;
		try {
			scanner = new Scanner(new File(shoeFile_in));
			String line = new String();
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				cards.addAll(Arrays.asList(line.split(" ", 0)));
			}
			scanner.close();
			char suit;
			String value;
			String temp;
			for(int i = 0; i < cards.size(); i++) {
				temp = cards.get(i);
				suit = temp.charAt(temp.length()-1);
				value = temp.substring(0, temp.length()-1);
				shoe.add(new Card(value, suit));
			}			
		} catch (FileNotFoundException e) {
			System.out.println("Invalid shoe file: " + e);
			System.exit(0);
		}
		nDecks = cards.size()/52;
	}
	
	/** Gets number of decks.
	 * 
	 * @return nDecks Number of decks.
	 */
	public int getNDecks() {
		return nDecks;
	}
	
	/** Shuffles the shoe.
	 * <p>
	 * Makes the shuffled cards not visible
	 */
	public void shuffle() {
		Collections.shuffle(shoe);
		for(Card c : shoe) {
			c.setIsUp(false);
		}
		resetNDealtCards();
	}

	/** Gets the top card from the shoe and places it ate the end of the shoe
	 * 
	 * @return card card got from the shoe.
	 * 
	 */
	public Card getCard() {
		Card card = shoe.get(0);
		shoe.add(card);
		shoe.remove(0);
		++nDealtCards;
		return card;
	}
	
	/**
	 * Gets the number of cards already dealt
	 * @return number of dealt cards
	 */
	public int getNDealtCards() {
		return nDealtCards;
	}
	
	private void resetNDealtCards() {
		nDealtCards = 0;
	}
	
	@Override
	public String toString() {
		String out = new String();
		for (int i = 0; i < shoe.size(); i++) {
			out += shoe.get(i).toString();
			out += '\n';
		}
		return out;
	}

}