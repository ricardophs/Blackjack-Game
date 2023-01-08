package blackjack;

import cardCounting.BettingStrategy;
import cardCounting.PlayerStrategy;

/** 
 * Class that gets the commands when in Simulation mode <p>
 * Implements the GameMode Interface <p>
 * The betting commands are obtained from a betting strategy, while the player commands are
 * obtained from a player strategy.
 */
public class Simulation implements GameMode {
	
	/**
	 * Number of shuffles until the simulation ends (return "q" command)
	 */
	private final int sNumber;
	/**
	 * Number of shuffles already performed
	 */
	private int currSNumber;
	/**
	 * Strategy used to get the player commands
	 */
	PlayerStrategy play_strat;
	/**
	 * Strategy used to get the betting commands
	 */
	BettingStrategy bet_strat;
	
	/** Initializes the parameters for the simulation
	 * 
	 * @param sNumber_in Stores the number of shuffles until the end of the simulation.
	 * @param play_s Stores the selection of the playing strategy
	 * @param bet_s Stores the selection of the betting strategy
	 * 
	 */
	public Simulation (int sNumber_in, PlayerStrategy play_s, BettingStrategy bet_s) {
		sNumber = sNumber_in;
		currSNumber = -1;
		play_strat = play_s;
		bet_strat = bet_s;
	}
	
	/** Gets the Player command as advised by the chosen Playing Strategy
	 * 
	 * @param nHands Player's number of hands (used by the playing Strategy to check is the player can split)
	 * @param p_hand Player's hand
	 * @param d_hand Dealer's hand
	 * @param bet Value of the bet
	 * @return best command according to the playing strategy or "q" if the simulation has ended
	 * 
	 */
	@Override
	public String getPlayCommand(int nHands, PlayerHand p_hand, Hand d_hand, int bet) {
		String cmd;
		if(currSNumber > sNumber)
			 cmd = "q";
		else
			cmd = play_strat.getNextPlay(nHands, p_hand, d_hand, bet);
		return cmd;
	}

	/** Gets the Betting command as advised by the chosen Betting Strategy
	 * 
	 * @return best bet according to the betting strategy or "q" if the simulation has ended
	 * 
	 */
	@Override
	public String getBetCommand() {
		String cmd;
		if(currSNumber > sNumber) {
			cmd = "q";
		}
		else
			cmd = "b " + bet_strat.getNextBet();
		return cmd;
	}
	
	/** Increments the current sNumber
	 */
	public void incCurrSNumber() {
		++currSNumber;
	}
	
	/** Only called by the Debug and Interactive modes, that implement the same interface as the Simulation
	 */
	@Override
	public String getPlayCommand() {
		return null;
	}
		
}