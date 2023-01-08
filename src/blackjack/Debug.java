package blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/** 
 * Class that gets the commands when in Debug mode <p>
 * Implements the GameMode Interface 
 */
public class Debug implements GameMode {

	/**
	 * List of readable commands
	 */
	ArrayList<String> cmds;
	/**
	 * File with all the commands
	 */
	String cmdFile;
	
	
	/** Debug Mode. Reads all the commands from the cmdFile and places them in an ArrayList of Strings
	 * <p>
	 * Separates the commands correctly, and joins commands that should be together, like b [bet value]. 
	 * Also checks if the bet value is an integer.
	 * 
	 * @param cmdFile_in Name of the file with the commands.
	 * 
	 */
	public Debug (String cmdFile_in) {
		
		cmdFile = cmdFile_in;		
		cmds = new ArrayList<String>();
		
		Scanner scanner;
		try {
			scanner = new Scanner(new File(cmdFile));
			String line = new String();
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				cmds.addAll(Arrays.asList(line.split(" ", 0)));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Invalid command file: " + e);
			System.exit(0);
		}		
		
		for (int i = 0; i < cmds.size(); i++) {
			if (cmds.get(i).equals("b") && (isNumeric(cmds.get(i+1)))) {
				cmds.add(i+1, cmds.get(i) + " " + cmds.get(i+1));
				cmds.remove(i);
				cmds.remove(i+1);
			}
		}
	}
	
	/** 
	 * Gets a valid command from the previously computed list of commands.
	 * <p>
	 * If the command isn't recognize, prints an error message and returns an empty string
	*/
	@Override
	public String getPlayCommand() {
		if(cmds.size() == 0) return "q";
		String next_cmd = cmds.get(0);
		cmds.remove(0);
		System.out.println("# " + next_cmd);
		if (next_cmd.length() <= 2) {
			if (next_cmd.equals("h"))
				return "h";
			else if (next_cmd.equals("s"))
				return "s";
			else if (next_cmd.equals("d"))
				return "d";
			else if (next_cmd.equals("b"))
				return "b";
			else if (next_cmd.equals("i"))
				return "i";
			else if (next_cmd.equals("u"))
				return "u";
			else if (next_cmd.equals("p"))
				return "p";
			else if (next_cmd.equals("2"))
				return "2";
			else if (next_cmd.equals("ad"))
				return "a";
			else if (next_cmd.equals("st"))
				return "t";
			else if (next_cmd.equals("$"))
				return "$";
			else if (next_cmd.equals("q"))
				return "q";
		}
		else if (next_cmd.charAt(0) == 'b')
			return next_cmd;
		
		System.out.println(next_cmd + ": illegal command");
		return "";
	}

	
	/** Converts all the commands to a string (testing only).
	 * 
	 * @return Resulting String with all the commands
	 * 
	 */
	@Override
	public String toString() {
		String out = new String();
		for (String cmd : cmds) {
			out += cmd;
			out += '\n';
		}
		return out;
	}

	/** Checks if a String is a number (useful to check the betting value).
	 * 
	 * @param strNum String 
	 * @return true If it is an integer
	 * @return false otherwise
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
	
	/** Same as in {@link getPlayCommand}
	 */
	@Override
	public String getBetCommand() {
		return getPlayCommand();
	}

	/** Same as in {@link getPlayCommand}
	 */
	@Override
	public String getPlayCommand(int nHands, PlayerHand p_hand, Hand d_hand, int bet) {
		return getPlayCommand();
	}
}