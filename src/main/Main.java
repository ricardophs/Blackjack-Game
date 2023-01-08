package main;

import blackjack.Game;

public class Main {
	
	private static Game game = null;
	
	public static void main(String args[]){
		if (args.length != 6 && args.length != 8) {
			System.out.println("Incorrect number of arguments");
			System.exit(0);
		}
		char Mode_in = args[0].charAt(1);
		int min_bet = 0, max_bet = 0, balance = 0;
		try {
			min_bet = Integer.parseInt(args[1]);
			max_bet = Integer.parseInt(args[2]);
		} catch (NumberFormatException nfe) {
			System.out.println("Invalid bet args: " + nfe);
			System.exit(0);
		}
		if (min_bet < 1 || (max_bet < 10*min_bet || max_bet > 20*min_bet)) {
			System.out.println("Invalid bet args");
			System.exit(0);				
		}
		try {
			balance = Integer.parseInt(args[3]);
		} catch (NumberFormatException nfe) {
			System.out.println("Invalid balance: " + nfe);
			System.exit(0);
		}
		if (balance < 50*min_bet) {
			System.out.println("Invalid balance");
			System.exit(0);				
		}
		if ((Mode_in) == 'i') {
			int shoe = 0, shuffle = 0;
			try {
				shoe = Integer.parseInt(args[4]);
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid number of decks: " + nfe);
				System.exit(0);
			}
			if (shoe < 4 || shoe > 8) {
				System.out.println("Invalid number of decks");
				System.exit(0);				
			}
			try {
				shuffle = Integer.parseInt(args[5]);
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid shuffle parameter: " + nfe);
				System.exit(0);
			}
			if (shuffle < 10 || shuffle > 100) {
				System.out.println("Invalid shuffle parameter");
				System.exit(0);				
			}
			game = new Game(Mode_in, min_bet, max_bet, balance, shoe, shuffle, -1, null);
		}
		else if ((Mode_in) == 'd') {
			game = new Game(Mode_in, min_bet, max_bet, balance, args[4], args[5]); 
		}
		else if ((Mode_in) == 's') {
			int shoe = 0, shuffle = 0, snumber = 0;
			try {
				shoe = Integer.parseInt(args[4]);
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid number of decks: " + nfe);
				System.exit(0);
			}
			if (shoe < 4 || shoe > 8) {
				System.out.println("Invalid number of decks");
				System.exit(0);				
			}
			try {
				shuffle = Integer.parseInt(args[5]);
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid shuffle parameter: " + nfe);
				System.exit(0);
			}
			if (shuffle < 10 || shuffle > 100) {
				System.out.println("Invalid shuffle parameter");
				System.exit(0);				
			}
			try {
				snumber = Integer.parseInt(args[6]);
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid sNumber parameter: " + nfe);
				System.exit(0);
			}
			if (snumber <= 0) {
				System.out.println("Invalid sNumber parameter");
				System.exit(0);				
			}
			game = new Game(Mode_in, min_bet, max_bet, balance, shoe, shuffle, snumber, args[7]);
		}
		else {
			System.out.println("Incorrect arguments");
			System.exit(0);			
		}
		
		game.play();
	}
}
