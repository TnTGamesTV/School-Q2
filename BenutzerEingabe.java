public class BenutzerEingabe {
	
	public static int[] leseCodeEin(int len, int min, int max) {
		int[] code = new int[len];
		try {
			System.out.println("Ihre Eingabe: ");
			String line = System.console().readLine();
			if (line.equals("ende")) {
				System.exit(0);
			}
			String[] farben = line.split(" ");
			
			for (int i = 0; i < farben.length; i++) {
				int element = Integer.parseInt(farben[i]);
				if ((element < min) || (element > max)) {
					throw new ArithmeticException("Wert ausserhalb des Bereichs " + min + "," + max);
				}
				code[i] = element;
				if (i >= code.length) {
					return code;
				}
			}
			
		}
		catch (ArrayIndexOutOfBoundsException aiobe) {
			System.out.println("Sie haben zu wenig Zahlen eingegeben. Mindestens " + len);
		}
		catch (NumberFormatException nfe) {
			System.out.println("Bitte geben sie ganze Zahlen ein: " + nfe.getMessage());
		}
		catch (ArithmeticException ae) {
			System.out.println(ae.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return code;
	}
}
