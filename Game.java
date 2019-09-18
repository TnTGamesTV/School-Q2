import java.util.Scanner;

public class Game {
	
	public static final int	CONST_MAXIMUM_TRIES	= 12;
	
	public static final int	CONST_SIZE_SOLUTION	= 4;
	
	/* We do not need this paramenter as we can use loesung.length */
	private int				codeLaenge;
	
	private int				defaultWert;
	
	private int[]			loesung;
	
	private int				maxVersuche;
	
	private int				maxWert;
	
	private int				minWert;
	
	private int[][]			spielfeld;
	
	private int				versuch;
	
	/* We use this flag to determine if the user has won or not */
	private boolean			winFlag;
	
	public void allesAufAnfang() {
		for (int i = 0; i < spielfeld.length; i++) { // Iterate over every row
			for (int j = 0; j < spielfeld[i].length; j++) { // and every cell
				spielfeld[i][j] = defaultWert; // and set default value
			}
		}
		
		versuch = 1; // First try
	}
	
	public int anzahlRichtigeZahlen(int runde) {
		int[] input = spielfeld[runde - 1]; // get user input for the required round
		int output = 0; // init result
		
		for (int i : input) { // for each value the user put in
			for (int possibleSolution : loesung) { // iterate over solution
				if (i == possibleSolution) { // and compare if 'i' (the input) is contained in solutions
					output++; // if so count up
				}
			}
		}
		
		return output; // and return the result
	}
	
	public void loesungAlsString() {
		String output = "["; // init our base
		
		for (int i = 0; i < loesung.length; i++) { // iterate over each solution
			output += loesung[i]; // append number to output
			
			if (i < (loesung.length - 2)) output += ", "; // if this is not the last solution add seperator
		}
		
		System.out.println("Lösung: " + output + "]"); // print result
	}
	
	public void spielen() {
		Scanner input = new Scanner(System.in);
		String line = "";
		
		System.out.println("Willkommen bei MasterMind.");
		System.out.println("Das Spiel hat begonnen.");
		System.out.println("Gebe deine Antwort in folgendem Format an: 1, 2, 3, 4");
		
		while (versuch <= maxVersuche) {
			line = input.next();
			
		}
		
		if (winFlag) {
			System.out.println("Du hast mit " + (versuch - 1) + " Versuchen gewonnen");
		} else {
			System.out.println("Du hast verloren!");
			loesungAlsString();
		}
		input.close();
	}
}
