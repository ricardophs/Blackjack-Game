package blackjack;

/** Class that implements the general statistics for a blackjack player (both dealer and actual player)
 * <p>
 * Stores information used by both game members when computing their Statistics: number of hands played and
 * number of blackjacks achieved.
 * <p>
 * It is also responsible for updating said information and computing the blackjacks ratios.
 * <p>
 * Is extended by the PlayerStats class
 * 
*/
public class Stats {

	protected int handsPlayed;
	protected int blackjacks;

	/** Initializes the statistics.
	*/
	public Stats() {
		handsPlayed = 0;
		blackjacks = 0;
	}
	
	/** Calculates the ratio of blackjacks per hand.
	 * 
	 * @return blackjacks/handsPlayed Ratio.
	 * 
	*/
	public double getBJavg() {
		return (double)blackjacks/handsPlayed;
	}
	
	/** Calculates the ratio of blackjacks per hand.
	*/
	public void incHandsPlayed() {
		++handsPlayed;
	}
	
	/** Updates the number of Blackjacks.
	*/
	public void incBlackjacks() {
		++blackjacks;
	}
}
