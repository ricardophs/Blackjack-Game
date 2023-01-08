package blackjack;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cardCounting.Ace5;
import cardCounting.Basic;
import cardCounting.BettingStrategy;
import cardCounting.HiLo;
import cardCounting.PlayerStrategy;
import cardCounting.StandardStrategy;


/** Class that implements a blackjack game
 * <p> Is responsible for creating all the objects that will  in a game: 
 * both the dealer (which will have a shoe) and the player, the Statistics objects 
 * (for the player and the dealer), the GameMode object, which will get the commands 
 * according to the mode that is being played, and the Betting and Playing card counting
 * strategies.  
 * <p> All the objects needed while running the program in whichever mode are created and initialized
 * at the beginning, in the constructor of this class.
*/
public class Game {
	
	/** Object that will get the game commands according to the game mode 
	 * (actual mode specified at run time). */
	private GameMode mode;
	/** Minimum value for the bet */
	private int minBet;
	/** Maximum value for the bet */	
	private int maxBet;
	/** Number of decks in the shoe 
	 * (needed for the debug mode, where the number of decks is computed while reading the shoe file
	 * and returned by the shoe object. It will be needed later in other objects) */	
	private int nDecks;
	/** Percentage of the shoe that needs to be played before there's a shuffle */	
	private int intShuffle;
	/** Signals if a deal ("d") command can be issued (the bet "b" was already placed) */	
	private boolean isReady = false;
	/** Signals if a round is about to start ({@code true}) */	
	private boolean startRound = false;
	/** {@code true} when the deck is to be shuffled. Defaults to {@code true} at the Game constructor 
	 * for the simulation and interactive modes (shuffle at the beggining) and is always {@code false}
	 * for the debug mode */	
	private boolean shuffling;
	/** Flag so that the dealer knows if there are still any hands in play before it's turn, that is, 
	 * if any of the player's hands hasn't busted or surrendered (or, in other words, if there is any 
	 * hand that stands). If there aren't any hands at play before the dealers turn, he will only flip 
	 * its hole card and automatically stand, since he already won to all players' hands. */	
	private boolean noHandsLeft;
	/** In Simulation mode the commands and its results aren't supposed to be printed to the console */	
	private boolean printFlag;
	/** Game player */	
	private Player player;
	/** Game dealer */	
	private Dealer dealer;
	/** dealer Statistics */	
	private Stats dStats;
	/** player Statistics (object will be created with type PlayerStats) */	
	private Stats pStats;
	/** Available betting strategies. Can be 1 or more (in the case of this version, 
	 * 1 or 2, but more betting strategies could be added).
	 * <p> For simplicity, the position 0 will always be the Standard betting Strategy, when available. */
	private ArrayList<BettingStrategy> bet_strat;
	/** Available playing strategies. Can be 1 or more (in the case of this version, 
	 * 1 or 2, but more playing strategies could be added).
	 * <p> For simplicity, the position 0 will always be the Hi-Lo playing Strategy, when available. */
	protected ArrayList<PlayerStrategy> game_strat;
	
	/** Constructor for the Interactive and Simulation Modes.
	 * 
	 * @param Mode_in Selection of the simulation/interactive mode.
	 * @param minBet_in Minimum value for the bet that is allowed.
	 * @param maxBet_in Maximum value for the bet that is allowed.
	 * @param balance_in Starting player balance.
	 * @param nDecks_in Number of decks in the shoe.
	 * @param intShuffle_in Percentage of the shoe that has to be played before shuffling.
	 * @param sNumber_in Number of shuffles until the end of the simulation (when in simulation mode).
	 * @param strategy_in Represents the strategy that will be used (when in simulation mode).
	 * <p>
	 * When in interactive, both of the betting and playing strategies are applied, since when an advice (ad)
	 * command is issued, we're supposed to get information for all the available card counting strategies.
	 * <p>
	 * When in simulation mode only on combination betting + playing strategy is created 
	 */
	public Game(char Mode_in, int minBet_in, int maxBet_in, int balance_in, int nDecks_in, int intShuffle_in, int sNumber_in, String strategy_in) {
		if (Mode_in == 'i') { // Interactive mode
			printFlag = true; // prints can be done
			mode = new Interative();
			/* Both playing strategies */
			game_strat = new ArrayList<PlayerStrategy>();
			game_strat.add(new HiLo(maxBet_in, 9, 11, nDecks_in));
			game_strat.add(new Basic(maxBet_in, 9, 11)); 
			/* Both betting strategies */
			bet_strat = new ArrayList<BettingStrategy>();
			bet_strat.add(new StandardStrategy(minBet_in, maxBet_in));
			bet_strat.add(new Ace5(minBet_in, maxBet_in));
		}
		else { // Simulation mode
			printFlag = false; // no prints can be done (except the statistics in the end)
			game_strat = new ArrayList<PlayerStrategy>();
			bet_strat = new ArrayList<BettingStrategy>();
			/* Only one betting and playing strategies, depending on the received arguments */
			if (strategy_in.equals("BS")) {
				game_strat.add(new Basic(maxBet_in, 9, 11));
				bet_strat.add(new StandardStrategy(minBet_in, maxBet_in));
			}
			else if (strategy_in.equals("BS-AF")) {
				game_strat.add(new Basic(maxBet_in, 9, 11));
				bet_strat.add(new Ace5(minBet, maxBet));
			}
			else if (strategy_in.equals("HL")) {
				game_strat.add(new HiLo(maxBet_in, 9, 11, nDecks_in));
				bet_strat.add(new StandardStrategy(minBet_in, maxBet_in));
			}
			else if (strategy_in.equals("HL-AF")) {
				game_strat.add(new HiLo(maxBet_in, 9, 11, nDecks_in));
				bet_strat.add(new Ace5(minBet_in, maxBet_in));
			}
			else {
				System.out.println("Invalid betting strategy");
				System.exit(0);				
			}
			mode = new Simulation(sNumber_in, game_strat.get(0), bet_strat.get(0));
		}
		/* In both cases, flags are set in a similar way, and both player and dealer, as well as their statistics
		 * are instantiated */
		minBet = minBet_in;
		maxBet = maxBet_in;
		nDecks = nDecks_in;
		intShuffle = intShuffle_in;
	
		shuffling = true;
		
		player = new Player(minBet, balance_in);
		dealer = new Dealer(nDecks_in);
		
		dStats = new Stats();
		pStats = new PlayerStats(balance_in);
		
	}

	/** Constructor for the Debug Mode.
	 * 
	 * @param Mode_in Debug mode.
	 * @param minBet_in Minimum value for the bet that is allowed.
	 * @param maxBet_in Maximum value for the bet that is allowed.
	 * @param balance_in Current balance.
	 * @param shoeFile_in File with the shoe going in the game.
	 * @param cmdFile_in Name of the file with the commands to be issued.
	 * <p>
	 * In Debug mode both betting and playing strategies are applied, since the advice command ("ad")
	 * can still be issued. 
	 */
	public Game(char Mode_in, int minBet_in, int maxBet_in, int balance_in, String shoeFile_in, String cmdFile_in) {
		printFlag = true; // Commands should be printed
		mode = new Debug(cmdFile_in);
		
		minBet = minBet_in;
		maxBet = maxBet_in;
	
		shuffling = false; // Shuffle is initialized to false and will always be false

		player = new Player(minBet, balance_in);
		dealer = new Dealer(shoeFile_in);
		
		dStats = new Stats();
		pStats = new PlayerStats(balance_in);
		
		// Get approximated number of decks from the shoe file read
		nDecks = dealer.shoe.getNDecks();
		/* Both playing and betting strategies */
		game_strat = new ArrayList<PlayerStrategy>();
		game_strat.add(new HiLo(maxBet_in, 9, 11, nDecks));
		game_strat.add(new Basic(maxBet_in, 9, 11));
		bet_strat = new ArrayList<BettingStrategy>();
		bet_strat.add(new StandardStrategy(minBet, maxBet));
		bet_strat.add(new Ace5(minBet, maxBet));
	}
	
	/** Receives a play command and puts it into a advice-friendly format, to be printed
	 * @param cmd advised command in short form
	 * @return advised command with the command name
	 */
	private String getFullAdvice(String cmd) {
		if (cmd.equals("s"))
			return "stand";
		else if (cmd.equals("i"))
 			return "insurance";
 		else if (cmd.equals("u"))
			return "surrender";
		else if (cmd.equals("p"))
			return "split";
		else if (cmd.equals("h"))
			return "hit";
		else if (cmd.equals("2"))
			return "double";
		else
			return "Invalid option";
	}
	
	/**
	 * Shuffles the shoe and resets the adequate counting strategies' counters;
	 * If the game is in simulation mode, increment the counter of the number of shuffles
	 * (since this is its stopping criterion)
	 */
	private void shuffleState() {
		if ((mode instanceof Simulation))
			((Simulation) mode).incCurrSNumber();
		for (int i = 0; i < bet_strat.size(); i++)
			bet_strat.get(i).resetCount();
		if ((game_strat.get(0) instanceof HiLo))
			((HiLo) game_strat.get(0)).resetCounts();
		if (printFlag)
			System.out.println("shuffling the shoe...");
		dealer.shuffle();
	}
	
	/**
	 * A bet is placed, if its value is valid, that is, if it is between min_bet and max_bet.
	 * If not, prints an error message.
	 * <p> If the bet is successfully placed, sets the new bet in the used betting strategies.
	 * @param bet betting value
	 * @return true if betting was successful (a valid bet was issued), false otherwise.
	 */
	private boolean bettingState(int bet) {
		if (bet < minBet || bet > maxBet) {
			if (printFlag)
				System.out.println("b " + bet + ": illegal command");
			return false;
		}
		player.placeBet(bet);
		for (int i = 0; i < bet_strat.size(); i++)
			bet_strat.get(i).setBet(bet);
		if (printFlag)
			System.out.println("player is betting " + bet);
		return true;
	}
	
	/**
	 * Prints the statistics information in the console, for both the player and the dealer, formated with 2 decimal places.
	 */
	private void printStatsState() {
		DecimalFormat df = new DecimalFormat("#.##");
		System.out.println("BJ P/D \t" + df.format(pStats.getBJavg())+ " / " + df.format(dStats.getBJavg()));
		System.out.println("Win  \t" + df.format(((PlayerStats) pStats).getWLPavg(1)));
		System.out.println("Lose \t" + df.format(((PlayerStats) pStats).getWLPavg(-1)));
		System.out.println("Push \t" + df.format(((PlayerStats) pStats).getWLPavg(0)));
		System.out.println("Balance\t" + player.getBalance() + " / " + 
				df.format(((PlayerStats) pStats).percentageOfGain(player.getBalance())));
	}
	
	
	/**
	 * Play State: first method called when starting a new blackjack game and at the beginning
	 * of a new round. Resets the dealer's and player's hands and checks if a shuffle is needed (if so, calls
	 * the shuffleState()). Reads a command from any source (depending on the game mode), and waits
	 * for a bet to be placed. When this happens, calls the bettingState, and if the bet is successfully placed 
	 * waits for a deal ("d") command to be issued so that it calls the playRound state (unless the game is in 
	 * Simulation mode, there, goes directly to the playRound state).
	 * <p> Also allows commands for printing statistics, player balance, betting advice and quiting the game.
	 */
	public void play() {
				
		while(true) {
			player.clearHands();
			dealer.clearHand();
			/* Shuffle if the number of dealt cards surpassed the intShuffle threshold,
			* unless the program is running on Debug Mode */
			if(!(mode instanceof Debug) && dealer.shoe.getNDealtCards() >= Math.ceil(intShuffle/100.0f * nDecks * 52)) {
				shuffling = true;
			}
			if(shuffling) {
				shuffleState();
				shuffling = false;
			}
			/* Gets a command from a source, either the command line, a file or the simulator 
			 * Valid commands at this stage: b [<value>] and d, in this order;
			 * isReady flags that a "d" command should be issued; no more "b" commands are accepted.*/
			String s = mode.getBetCommand();
			if(s.isBlank()) continue; // Command in a bad format; "\0" was returned;
			String[] toks = s.split(" ", 0);
			if(toks[0].charAt(0) == 'b') {
				if(isReady) {
					if (printFlag)
						System.out.println(s + ": illegal command");
				}
				else {
					int bet = (toks.length == 1) ? minBet : Integer.parseInt(toks[1]);
					isReady = bettingState(bet);
					if(mode instanceof Simulation) startRound = true;
				}
			}else if(toks[0].charAt(0) == 'd') {
				if(isReady) {
					startRound = true;
				} else {
					if (printFlag)
						System.out.println(toks[0] + ": illegal command");
				}
			} else if(toks[0].charAt(0) == '$') {
				if (printFlag)
					System.out.println("Player's current balance is " + player.getBalance());
			} else if(toks[0].charAt(0) == 't') {
				printStatsState();
			} else if(toks[0].charAt(0) == 'a') {
				if(isReady) {
					if (printFlag)
						System.out.println(s + ": illegal command");
				}
				else {
					if (printFlag) {
						System.out.println("Ace5 \t\tbet " + bet_strat.get(1).getNextBet());
						System.out.println("Standard Bet\tbet " + bet_strat.get(0).getNextBet());
					}
				}
			} else if(toks[0].charAt(0) == 'q') {
				if (printFlag)
					System.out.println("bye");
				else
					printStatsState();
				System.exit(0);				
			} else {
				if (printFlag)
					System.out.println(s + ": illegal command");
			}
			
			if(startRound) {
				playRound();
				isReady = false;
				startRound = false;
			}
		}
	}
		
	/**
	 * The dealer deals cards to himself (firstly, and with one hole card), and then to the player.
	 * All the betting and playing strategies are updated, as well as the statistics information
	 */
	private void dealState() {
		for(int i = 0; i < 2; ++i) {
			Card c = dealer.dealCards();
			if (i == 0) {
				for (int j = 0; j < bet_strat.size(); j++)
					bet_strat.get(j).updateCount(c);
				if(game_strat.get(0) instanceof HiLo)
					((HiLo) game_strat.get(0)).updateCounts(c);
			}
			dealer.addCard(c);
		}
		dStats.incHandsPlayed();
		for(int i = 0; i < 2; ++i) {
			Card c = dealer.dealCards();
			for (int j = 0; j < bet_strat.size(); j++)
				bet_strat.get(j).updateCount(c);
			if(game_strat.get(0) instanceof HiLo)
				((HiLo) game_strat.get(0)).updateCounts(c);
			player.addCard(0, c);
		}
		pStats.incHandsPlayed();
	}
	
	/**
	 * A player's turn: the player plays all his hands until he stands, surrenders or busts in all his hands
	 * (up to a maximum of four).
	 * <p> The playing commands are obtained depending on the game mode. Throughout the player's turn, 
	 * all the counts related to betting and playing strategies, as well as to the statistics, are updated.
	 */
	private void playersTurn() {
		
		String line;
		String hand_index;
		int print_index;
		
		for(int i = 0; i < player.getNHands(); ++i) {
			print_index = -1;
			hand_index = "";
			// If there was a split before and the current hand only has one card
			if(player.hands.get(i).isSplit() && player.hands.get(i).getNCards() == 1) {
				// get a new card and update all the game information
				Card c = dealer.dealCards();
				for (int j = 0; j < bet_strat.size(); j++)
					bet_strat.get(j).updateCount(c);
				if(game_strat.get(0) instanceof HiLo)
					((HiLo) game_strat.get(0)).updateCounts(c);
				player.addCard(i, c);
				if(player.hands.get(i).getFirst().getValue().equals("A") && !(c.getValue().equals("A"))) {
					player.stand(i);
				}
			}
			// If the player has more than one hand (already splitted)
			if(player.getNHands() > 1) {
				print_index = i;
				hand_index =  " [" + (i+1) + "] ";
				if (printFlag) {
					if (i == 0)
						System.out.println("playing 1st hand...");
					else if (i == 1)
						System.out.println("playing 2nd hand...");
					else if (i == 2)
						System.out.println("playing 3rd hand...");
					else
						System.out.println("playing 4th hand...");
					player.printPlayersHand(print_index);
				}
			}
			/* if the player already stood (or was forced to do so, for example, after splitting a pair of 
			* and receiving a card different than an Ace in any of those hands) */
			if(player.isStandingHand(i)) {
				continue;
			}
			
			/* Gets the play from any of the available sources, depending on the game mode */
			while(true) {
				if(mode instanceof Simulation) 
					// get command from a playing strategy
					line = mode.getPlayCommand(player.getNHands(), player.hands.get(i), dealer.hand, player.getBet());
				else
					// get command from console or file
					line = mode.getPlayCommand(); 
				if(line.isBlank()) continue; // Blank line return, invalid command was detected
				char cmd = line.charAt(0);
				// sets some state variables according to the play
				if(cmd == 'h') {
					if ((player.hands.get(i).isSplit() && (player.hands.get(i).getNCards() == 2) && (player.hands.get(i).getFirst().getValue().equals("A")))) {
						if (printFlag)
							System.out.println(line + ": illegal command");
					}
					else
						player.hit(i);
				}
				else if(cmd == 's') {
					player.stand(i);
				} 
				else if(cmd == 'i') {
					if (player.hands.get(i).isOpening() && dealer.hand.getFirst().getValue().equals("A") &&
							!player.isInsuring()) {
						player.insurance();
						if (printFlag)
							System.out.println("player is insuring");
					}
					else {
						if (printFlag)
							System.out.println(line + ": illegal command");
					}
				}
				else if(cmd == 'u') {
					if (player.hands.get(i).getNCards()==2)
						player.surrender(i);
					else {
						if (printFlag)
							System.out.println(line + ": illegal command");
					}
				}
				else if(cmd == 'p') {
					if (player.hands.get(i).isPair() && (player.getNHands() <= 3))
						player.setIsSplitting(true);
					else {
						if (printFlag)
							System.out.println(line + ": illegal command");
					}
				}
				else if(cmd == '2') {
					if (player.hands.get(i).getNCards() == 2 && player.hands.get(i).getValue()>8 && player.hands.get(i).getValue()<12)
						player.doubleD(i);
					else {
						if (printFlag)
							System.out.println(line + ": illegal command");					
					}
				} 
				else if(cmd == 'a') { 
					String play = game_strat.get(1).getNextPlay(player.getNHands(), player.hands.get(i), dealer.hand, player.getBet());
					if (printFlag)
						System.out.println("Basic\t\t" + getFullAdvice(play));
					play = game_strat.get(0).getNextPlay(player.getNHands(), player.hands.get(i), dealer.hand, player.getBet());
					if (printFlag)
						System.out.println("HiLo\t\t" + getFullAdvice(play));
				} else if(cmd == 't') {
					if (printFlag)
						printStatsState();
				} else if(cmd == '$') {
					if (printFlag)
						System.out.println("Player's current balance is " + player.getBalance());
				} else if(cmd == 'q') {
					if (printFlag)
						System.out.println("bye");
					System.exit(0);
				} else {
					if (printFlag)
						System.out.println(line + ": illegal command");					
				}
				
				// Uses the previously set state variables to actually perform the corresponding play
				if(player.isHittingHand(i)) {
					// gets a card and updates all the game information
					if(!player.isDoubleDHand(i)) {
						if (printFlag)
							System.out.println("player hits");
					}
					Card c = dealer.dealCards();
					for (int j = 0; j < bet_strat.size(); j++)
						bet_strat.get(j).updateCount(c);
					if(game_strat.get(0) instanceof HiLo)
						((HiLo) game_strat.get(0)).updateCounts(c);
					player.addCard(i, c);
					if (printFlag)
						player.printPlayersHand(print_index);
					player.hands.get(i).setHitting(false);
					// If a player busts, set its state and go to the next hand (if there is one)
					if(player.hands.get(i).isBust()) {
						if (printFlag)
							System.out.println("player busts" + hand_index);
						break;
					}
					// If the player doubled down, he is forced to stand afterwards (could only take one more card)
					if(player.isDoubleDHand(i)) {
						player.stand(i);
						break;					
					}
				} // Player stands this hand; set the state and go to the next one (if there's one)
				else if(player.isStandingHand(i)) {
					if (printFlag)
						System.out.println("player stands" + hand_index);
					break;
				} // Player surrenders this hand; set the state and go to the next one (if there's one)
				else if(player.isSurrendingHand(i)) {
					if (printFlag)
						System.out.println("player is surrendering" + hand_index);
					break;
				} /* Player splits: performs the split action, adds a card to the first of the splitted hands
				 and updates the information accordingly. Allows the player to play on this hand */
				else if(player.isSplitting()) {
					pStats.incHandsPlayed();
					if (printFlag)
						System.out.println("player is splitting");
					player.split(i);
					Card c = dealer.dealCards();
					for (int j = 0; j < bet_strat.size(); j++)
						bet_strat.get(j).updateCount(c);
					if(game_strat.get(0) instanceof HiLo)
						((HiLo) game_strat.get(0)).updateCounts(c);	
					player.addCard(i, c);
					// If the player splitted a pair of aces and the next card is not an ace, the player can no longer hit
					if(player.hands.get(i).getFirst().getValue().equals("A") && !(c.getValue().equals("A")))
						player.stand(i);
					i--;
					break;
				}
			}
		}
	}
	
	/**
	 * The dealer's turn: Starts by turning up his hole card. Hits until an hand value of 
	 * at least 17 is attained, then stands; if all the player's hands have busted or surrender, then the dealer
	 * stands immediately after revealing his hole card, since he already won this round.
	 * <p> For every card added (hit), game information is updated. 
	 */
	private void dealersTurn() {

		dealer.setVisible();
		for (int j = 0; j < bet_strat.size(); j++)
			bet_strat.get(j).updateCount(dealer.hand.cards.get(1));
		if(game_strat.get(0) instanceof HiLo)
			((HiLo) game_strat.get(0)).updateCounts(dealer.hand.getFirst());
		
		while(true) {
			if (printFlag)
			dealer.printDealersHand();
			int dHandValue = dealer.hand.getValue();
			if(dHandValue > 21) {
				if (printFlag)
					System.out.println("dealer busts");
				break;
			}
			if(dHandValue < 17 && !noHandsLeft) {
				Card c = dealer.dealCards();
				for (int j = 0; j < bet_strat.size(); j++)
					bet_strat.get(j).updateCount(c);
				if(game_strat.get(0) instanceof HiLo)
					((HiLo) game_strat.get(0)).updateCounts(c);
				dealer.addCard(c);
				if (printFlag)
					System.out.println("dealer hits");
			} else {
				dealer.hand.setIsStanding(true);
				if (printFlag)
					System.out.println("dealer stands");
				break;
			}
		}
	}
	
	/**
	 * Called by the play state when a deal command is issued, so a new round starts.
	 * <p> Starts by calling the dealState, printing both the dealer and player hands (if
	 * prints are allowed). Afterwards, calls the playersTurn state. Computes if there are any player's
	 * hands still in game and then calls the dealersTurn state. Lastly, checks if there is the need 
	 * to print any blackjack information and computes the round results, by calling the resultsState, 
	 * updating the game information accordingly. 
	 */
	public void playRound() {
		
		dealState();
		
		if (printFlag) {
			dealer.printDealersHand();
			player.printPlayersHand(-1);
		}
		
		playersTurn();
		
		noHandsLeft = true;
		for(PlayerHand h : player.hands) {
			if(h.isStanding()) {
				noHandsLeft = false;
				break;
			}
		}

		dealersTurn();
		
		if (dealer.hand.checkBlackjack()) {
			if (printFlag)
				System.out.println("blackjack!!");
		}
		else
			for(int i = 0; i < player.getNHands(); ++i)
				if (player.hands.get(i).checkBlackjack()) {
					if (printFlag)
						System.out.println("blackjack!!");
						break;
				}
		
		resultsState();
		
	}
	
	/**
	 * For each player hand, checks the round result, updates the player's balance accordingly,
	 * prints the right information (if prints are allowed) and updates all the game information (
	 * betting strategies counts and bet values and statistics values).
	 * <p>
	 * Calls an auxiliary function that helps to determine the round's result.
	 */
	private void resultsState() {

		if (dealer.hand.checkBlackjack()) dStats.incBlackjacks();
		for(int i = 0; i < player.getNHands(); ++i) {
			if (player.hands.get(i).checkBlackjack()) pStats.incBlackjacks();
			float mult = 1;
			int res;
			int bet =  player.hands.get(i).getBet();
			String res_str = new String();
			if(player.isInsuring() && dealer.hand.checkBlackjack()) {
				if (printFlag)
					System.out.println("Player wins insurance");
				player.updateBalance(2*bet);
			}
			if(player.hands.get(i).isSurrender()) {
				mult = 0.5f;
				res = -1;
				res_str += "loses";
			}
			else {
				res = result(i);
				if(res != 0) {
					if(res == -1) {
						mult = 0;
						res_str += "loses";
					} else {
						boolean splited_blackjack = (player.hands.get(i).isSplit() && 
								player.hands.get(i).cards.get(0).getValue().equals("A"));
						mult = (player.hands.get(i).checkBlackjack() && !(splited_blackjack) )  ? 2.5f : 2;
						res_str += "wins";
					}
				} else
					res_str += "pushes";
			}
				
			((PlayerStats) pStats).incWLP(res);
			if(bet_strat.get(0) instanceof StandardStrategy)
				((StandardStrategy) bet_strat.get(0)).updateBet(res);
			player.updateBalance(mult*bet);
			
			String hand_index = "";
			if(player.getNHands() > 1)
				hand_index = " [" + (i+1) + "]";
			if (printFlag)
				System.out.println("Player " + res_str + hand_index + " and his current balance is " + player.getBalance());
		}
		if (printFlag)
			System.out.println();
	}
	
	/** Calculates the results
	 * 
	 * @param i player hand in which the result is being computed
	 * @return result: 1 - player wins, 0 - player pushes, -1 - player loses
	 * 
	 */
	private int result(int i) {
		if(player.hands.get(i).isBust()) return -1;
		if(dealer.hand.isBust()) return 1;
		if (player.hands.get(i).checkBlackjack())
			return dealer.hand.checkBlackjack() ? 0 : 1;
		else if (dealer.hand.checkBlackjack()) 
			return -1;
		int playerScore = player.hands.get(i).getValue();
		int dealerScore = dealer.hand.getValue();
		if (playerScore < dealerScore)
			return -1;
		else if (playerScore > dealerScore)
			return 1;
		else
			return 0;
	}

}


