package blackjack;

import java.util.Scanner;

/** 
 * Class that gets the commands when in Interactive mode <p>
 * Implements the GameMode Interface 
 */
public class Interative implements GameMode {

	/**
	 * Scanner variable to read data from the console
	 */
	private static Scanner kb;
	
	/**
	 * Creates a new Scanner to read from the console
	 */
	public Interative () {
		kb = new Scanner(System.in);
	}
	
	/** Gets a command from the console line
	 * <p>
	 * Checks if the command is valid and well formatted. If not, prints and error message and returns an empty String
	 * @return Command identifier for well formatted commands, otherwise an empty string
	 */
	@Override
	public String getPlayCommand() {
		String line = kb.nextLine();
		if (line.length() <= 2) {
			if (line.equals("h"))
				return "h";
			else if (line.equals("s"))
				return "s";
			else if (line.equals("d"))
				return "d";
			else if (line.equals("b"))
				return "b";
			else if (line.equals("i"))
				return "i";
			else if (line.equals("u"))
				return "u";
			else if (line.equals("p"))
				return "p";
			else if (line.equals("2"))
				return "2";
			else if (line.equals("ad"))
				return "a";
			else if (line.equals("st"))
				return "t";
			else if (line.equals("$"))
				return "$";
			else if (line.equals("q"))
				return "q";
		}
		else if ((line.charAt(0) == 'b') && (line.charAt(1) == ' '))  {
			if (isNumeric(line.substring(2)) == true) {
				return line;					
			}
		}
		System.out.println(line + ": illegal command");
		return "";
	}

	/** Checks if it is reading a number.
	 * 
	 * @param strNum String.
	 * @return true If the string is a number.
	 * @return false If the string is not a number.
	 * 
	 */
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

	/** The same as in getPlayCommand(): for the interactive mode both commands come from the same place (console)
	 */
	@Override
	public String getBetCommand() {
		return getPlayCommand();
	}

	/** The same as in getPlayCommand(): this version of the method is only called when the caller class is of type Simulation
	 */
	@Override
	public String getPlayCommand(int nHands, PlayerHand p_hand, Hand d_hand, int bet) {
		return getPlayCommand();
	}
		
}
