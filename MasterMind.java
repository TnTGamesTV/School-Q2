import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * MasterMind Spielregeln:
 * - Codelaenge = 4
 * - max Versuche = 12
 * - Zahlen von 0 bis 6 erlaubt
 * - Zahlen duerfen nicht doppelt vorkommen
 * 
 * @version Aufgabe 2
 */
public class MasterMind {
	
	/* We do not need this paramenter as we can use loesung.length */
	private int		codeLaenge;
	
	private int		defaultWert;
	
	private int[]	loesung;
	
	private int		maxVersuche;
	
	private int		maxWert;
	
	private int		minWert;
	
	private int[][]	spielfeld;
	
	private int		versuch;
	
	/* We use this flag to determine if the user has won or not */
	private boolean	winFlag;
	
	public MasterMind() {
		codeLaenge = 4;
		defaultWert = -1;
		minWert = 0;
		maxWert = 6;
		maxVersuche = 12;
		
		spielfeld = new int[maxVersuche][codeLaenge];
	}
	
	/**
	 * Die Methode setzt die Werte vom spielfeld auf den defaultWert
	 * sowie das Attribut versuch auf 1. Damit ist die Ausgangsstellung
	 * wieder hergestellt und ein neues Spiel kann gestartet werden.
	 */
	public void allesAufAnfang() {
		for (int i = 0; i < spielfeld.length; i++) { // Iterate over every row
			for (int j = 0; j < spielfeld[i].length; j++) { // and every cell
				spielfeld[i][j] = defaultWert; // and set default value
			}
		}
		
		versuch = 1; // First try
		loesung = getZufallsCode(); // Set new code
	}
	
	/**
	 * Diese Methode betrachtet die x-te Runde und vergleicht die entsprechenden
	 * Positionen des Spielfeldes mit denen der Lösungskombination. Sie gibt die
	 * Anzahl der "SpielSteine", welche an der richtigen Position gesetzt
	 * wurden, zurück.
	 * Beispiel:
	 * Lösung = 6 5 4 3
	 * Eingabe = 6 0 4 1
	 * Dann gibt diese Methode 2 zurück.
	 */
	public int anzahlRichtigePositionen(int runde) {
		int anzahlTreffer = 0;
		int i = 0;
		while (i < codeLaenge) {
			// Hier wird der Code aus dem Spielfeld an der Stelle (runde - 1) [Arrays fangen
			// mit Index 0 an] genommen
			// und jede Zahl dieses Codes wird mit dem Element an der gleichen Stelle aus
			// der Lösung verglichen.
			// Wenn eine Zahl übereinstimmt, wird die Variable anzahlTreffer um 1
			// hochgezählt.
			if (spielfeld[runde - 1][i] == loesung[i]) {
				anzahlTreffer++;
			}
			i++;
		}
		return anzahlTreffer;
	}
	
	/**
	 * Diese Methode betrachtet die x-te Runde und vergleicht die entsprechenden
	 * Zahlen des Spielfeldes mit denen der Lösungskombination. Sie zählt die
	 * Anzahl der "SpielSteine" des Lösungsvorschlages, die in der
	 * Lösungskombination enthalten sind.
	 * Beispiel:
	 * Lösung = 6 5 4 3
	 * Eingabe = 4 6 5 1
	 * Dann gibt diese Methode 3 zurück.
	 */
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
	
	public int[] getZufallsCode() {
		List<Integer> used = new ArrayList<>();
		int[] output = new int[codeLaenge];
		
		for (int i = 0; i < output.length; i++) {
			output[i] = (int) (Math.random() * maxWert + 1);
			
			while (used.contains(output[i])) {
				output[i] = (int) (Math.random() * maxWert + 1);
			}
			
			used.add(output[i]);
		}
		
		return output;
	}
	
	public int[] leseCodeEin(Scanner scanner) {
		int[] output = new int[codeLaenge];
		int counter = 0;
		
		String rawInput = scanner.nextLine();
		
		for (String rawNumber : rawInput.split(" ")) {
			int number = Integer.parseInt(rawNumber);
			
			if (number < minWert || number > maxWert) {
				throw new IllegalArgumentException("Der gew�hlte Wert ist ausserhalb der gesetzten Grenzen!");
			} else {
				for (int alreadyInOutput : output) {
					if (alreadyInOutput == number) {
						throw new IllegalArgumentException("Es existieren doppelte Werte");
					}
				}
				output[counter] = number;
				counter++;
			}
		}
		return output;
	}
	
	/**
	 * @return Gibt die Lösung in folgendem Format aus:
	 *         [zahl1,zahl2, ..., zahlN]
	 *         Wobei N die Länge der Lösungskombination ist.
	 */
	public String loesungAlsString() {
		String output = "["; // init our base
		
		for (int i = 0; i < loesung.length; i++) { // iterate over each solution
			output += loesung[i]; // append number to output
			
			if (i < (loesung.length - 2)) output += ", "; // if this is not the last solution add seperator
		}
		
		return output + "]";
	}
	
	/**
	 * Diese Methode ermöglicht es einem Benutzer ihr Mastermindspiel zu spielen.
	 * Lassen Sie den Benutzer solange Codes über die Konsole eingeben,
	 * bis er entweder die richtige Kombination erraten hat,
	 * oder bis er das Spiel verloren hat (nach maxVersuche).
	 * Geben Sie dem Benutzer anschließend aus, in wie vielen Zügen er gewonnen
	 * hat
	 * bzw. welche Kombination er hätte erraten müssen.
	 */
	public void spielen() {
		System.out.println("Willkommen bei MasterMind.");
		System.out.println("Das Spiel hat begonnen.");
		System.out.println("Gebe deine Antwort in folgendem Format an: 1 2 3 4");
		
		Scanner scanner = new Scanner(System.in);
		
		allesAufAnfang();
		
		while (versuch <= maxVersuche) {
			int[] input = leseCodeEin(scanner);
			spielfeld[versuch - 1] = input;
			
			int korrektePositionen = anzahlRichtigePositionen(versuch);
			int korrekteZahlen = anzahlRichtigeZahlen(versuch);
			
			if (korrektePositionen == codeLaenge && korrekteZahlen == codeLaenge) {
				winFlag = true;
				break;
			} else {
				versuch++;
				System.out.println("Debug " + versuch);
				System.out.println("Antwort: " + korrektePositionen + " mal schwarz und " + korrekteZahlen + " mal wei�");
			}
		}
		
		if (winFlag) {
			System.out.println("Du hast mit " + (versuch) + " Versuchen gewonnen");
		} else {
			System.out.println("Du hast verloren!");
			System.out.println("Korrekter Code" + loesungAlsString());
		}
		scanner.close();
	}
}