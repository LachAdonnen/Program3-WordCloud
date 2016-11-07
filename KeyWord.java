/**
 * @author amcclain
 *
 */
public class KeyWord implements Comparable<KeyWord>, Prioritizable {
	private String word;
	private int occurrences;
	
	public KeyWord(String word) throws IllegalArgumentException {
		if (word == null || word.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.word = word.toLowerCase();
		occurrences = 0;
	}
	
	@Override
	public int getPriority() { return this.getOccurrences(); }

	@Override
	public int compareTo(KeyWord other) {
		return this.word.compareToIgnoreCase(other.getWord());
	}

	public boolean equals(KeyWord other) {
		if (other == null) { return false; }
		return (this.compareTo(other) == 0);
	}
	
	public int getOccurrences() { return this.occurrences; }
	
	public String getWord() { return this.word; }
	
	public void increment() { this.occurrences++; }
}
