package blackjack;

/**
 * Interface for a Game mode.
 * <p>
 * Depending on the game mode, the commands (betting and plays) are obtained from different sources.
 * <p>
 * Interactive: from the command line <p>
 * Debug: from the commands file <p>
 * Simulation: from a betting Strategy and a Counting strategy (hence the extra arguments, that are needed when
 * calling such strategies).
 */

interface GameMode {
		
	public String getBetCommand();
	public String getPlayCommand();
	public String getPlayCommand(int nHands, PlayerHand p_hand, Hand d_hand, int bet);

}
