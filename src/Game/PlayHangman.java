package Game;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PlayHangman {

	// MAX TRIES 
	private static final int MAX_TRIES = 8;
	// Word LIST
	private static final String[] WORDS = { "mouse", "monitor", "desktop", "laptop", "keyboard", "software", "hardware",
			"firmware", "network", "digit", "language", "development", "program", "computer", "connector", "database",
			"query", "constraint", "disk", "protocol", "debug", "breakpoint", "source", "branch", "device", "printer" };
	
	
	
	
	// LETTERS
	private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

	public static void main(String[] args) {
		
		// OPEN SCANNER
		Scanner sc = new Scanner(System.in);

		System.out.println("Juego del ahorcado");

		boolean exit = true;
		do {
			// Choose random word
			String secret = chooseWord();
			// Play
			playHangman(secret, sc);
			boolean answered = false;
			while (!answered) {
				// Keep asking to play
				System.out.println("Deseas volver a jugar([s]/n)");
				String s = sc.nextLine().toLowerCase();
				// Check the answer
				if (s.length() == 0 || s.equals("s")) {
					secret = chooseWord();
					answered = true;
					exit = false;
				} else if (s.equals("n")) {
					answered = true;
					exit = true;
				} else {
					answered = false;
				}
			}

			

		} while (!exit); // Play while the user wants
		// Close scanner
		sc.close();
	}

	/**
	 * Selects a random word from the String array
	 * 
	 * @return String with the randomized word
	 */
	private static String chooseWord() {
		// elegir una palabra al azar
		Random rnd = new Random();
		int index = rnd.nextInt(WORDS.length);
		return WORDS[index];
	}

	/**
	 * Returns if the word has been discovered
	 * 
	 * @param secret  String with the secret word
	 * @param letters A list of the letters the user has tried
	 * @return true, if the user found all the letters of the word
	 *         secreta, false if not
	 */
	private static boolean isWordDiscovered(String secret, ArrayList<Character> letters) {
		if (secret.equals(getSecretWord(secret, letters))) {
			return true;
		} else
			return false;

	}

	/**
	 * Returns the letters discovered including (underscore) where the letter is not known.
	 * 
	 * 
	 * @param secret  Secret word 
	 * @param letters A list of the letters the user has tried
	 * @return String Returns the letters discovered including (underscore) where the letter is not known.
	 */
	private static String getSecretWord(String secret, ArrayList<Character> letters) {

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < secret.length(); i++) {

			if (letters.contains(secret.charAt(i))) {
				builder.append(secret.charAt(i));
			} else {
				builder.append("_");
			}
		}

		return builder.toString();
	}

	/**
	 * Returns letters that are not used by the user
	 * 
	 * @param letters A list of the letters the user has tried
	 * @return String Returns all the letters that are not user by the user
	 */
	private static String getNotGuessedLetters(ArrayList<Character> letters) {
		StringBuilder no = new StringBuilder();
		for (int i = 0; i < LETTERS.length(); i++) {
			char c = LETTERS.charAt(i);
			if (!letters.contains(c))
				no.append(c);

		}

		return no.toString();
	}

	/** 
	 * Starts the game
	 * 
	 * @param secret Secret word to guess
	 * @param sc     Open the scanner
	 */
	private static void playHangman(String secret, Scanner sc) {
		// MAX TRIES INITIALIZED
		int tries = MAX_TRIES;
		// Letters used by the user
		ArrayList<Character> letters = new ArrayList<>();
		// Show number of letters the word has
		System.out.println("The secret word has " + secret.length() + " letters");

		
		while (tries > 0) {
			if (tries == 0) {
				// LOST
				System.out.println("YOU LOST, TOO MUCH TRIES");
				break;
			}
			// Show INFO
			System.out.println("INFO");
			System.out.println(getSecretWord(secret, letters));

			System.out.println("Available letters: "+ getNotGuessedLetters(letters));
			System.out.println("Remaining tries: " + tries);

			// Ask for a letter
			System.out.println("Write a letter");
			char letra = sc.nextLine().toLowerCase().charAt(0);

			// Check if it exists already
			if (letters.contains(letra)) {
				// Warn user about it
				System.out.println(String.format("The letter '%c' has been introduced already.", letra));
			} else {
				
				// Add letter to the arrayList
				letters.add(letra);

				if (secret.contains(Character.toString(letra))) {
					// Letter discovered
					tries++; // YEAH THIS IS A CRAP SOLUTION
					System.out.println(String.format("Nice, you discovered the letter."));

				}
			}
			if (isWordDiscovered(secret, letters)) {
				// GAME OVER YOU WON
				System.out.println("WORD DISCOVERED, CONGRATULATIONS!!");
				break;
			}

			tries--;

		}

		System.out.println();
		

	}

}
