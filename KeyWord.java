///////////////////////////////////////////////////////////////////////////////
// Title:            Prog3-WordCloud
// Files:            KeyWord.java
// Semester:         Fall 2016
//
// Author:           Alex McClain, gamcclain@wisc.edu
// CS Login:         gamcclain@wisc.edu
// Lecturer's Name:  Charles Fischer
///////////////////////////////////////////////////////////////////////////////
/**
 * A KeyWord stores a single word as a String along with a number indicating
 * the occurrences of that word, likely within a larger text.
 * @author Alex McClain
 */
public class KeyWord implements Comparable<KeyWord>, Prioritizable {
	// The word to track
	private String word;
	// The number of times the word has appeared
	private int occurrences;
	
	/**
	 * Constructs a new keyword with the given word and 0 occurrences.
	 * @param word The word found in the text
	 * @throws IllegalArgumentException
	 */
	public KeyWord(String word) throws IllegalArgumentException {
		if (word == null || word.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.word = word.toLowerCase();
		occurrences = 0;
	}
	
	@Override
	/**
	 * Returns the number of occurrences of the word as the priority.
	 */
	public int getPriority() { return this.getOccurrences(); }

	@Override
	/**
	 * Compares the words of two KeyWords using String sort.
	 */
	public int compareTo(KeyWord other) {
		return this.word.compareToIgnoreCase(other.getWord());
	}

	/**
	 * Returns true if the two KeyWords have the same word.
	 * @param other The KeyWord to compare against.
	 * @return Whether the two KeyWords have the same word.
	 */
	public boolean equals(KeyWord other) {
		if (other == null) { return false; }
		return (this.compareTo(other) == 0);
	}
	
	/**
	 * Returns the number of occurrences for this KeyWord.
	 * @return The number of occurrences within the text.
	 */
	public int getOccurrences() { return this.occurrences; }
	
	/**
	 * Returns the word for this KeyWord.
	 * @return The word found in the text.
	 */
	public String getWord() { return this.word; }
	
	/**
	 * Increases the number of occurrences for this KeyWord by 1.
	 */
	public void increment() { this.occurrences++; }
}
