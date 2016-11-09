///////////////////////////////////////////////////////////////////////////////
// Title:            Prog3-WordCloud
// Files:            WordCloudGenerator.java
// Semester:         Fall 2016
//
// Author:           Alex McClain, gamcclain@wisc.edu
// CS Login:         gamcclain@wisc.edu
// Lecturer's Name:  Charles Fischer
///////////////////////////////////////////////////////////////////////////////
import java.util.*;
import java.io.*;

/**
 * Main class for the word cloud generator. Takes in files for both input and
 * output and calculates the word volume and output graphic HTML.
 * @author amcclain
 */
public class WordCloudGenerator {
    
	/**
	 * Main method responsible for reading in arguments, processing texts, and
	 * generating the word cloud HTML.
	 * @param args Array of command line inputs.
	 * [0] - inputFile
	 * [1] - outputFile
	 * [2] - ignoreFile
	 * [3] - maxWords
	 */
    public static void main(String[] args) {
        Scanner in = null;         // for input from text file
        PrintStream out = null;    // for output to html file
        Scanner inIgnore = null;   // for input from ignore file
        // Dictionary for words in the input file
        DictionaryADT<KeyWord> dictionary = new BSTDictionary<KeyWord>();  

        // Check the command-line arguments and set up the input and output
        validateCmdLineArgs(args); // Exits if not valid
        
        String fileName = "";
        // Open all necessary files for reading/writing
        try {
        	fileName = args[0];
            in = new Scanner(new File(fileName));
        	fileName = args[1];
            out = new PrintStream(new File(fileName));
        	fileName = args[2];
            inIgnore = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException e) {
        	System.out.println("Error: cannot access file " + fileName);
        	System.exit(0);
        }
        
        int maxWords;
        try { maxWords = Integer.valueOf(args[3]); }
        catch (NumberFormatException e) { maxWords = -1; }
        validatePositiveInteger(maxWords); // Exits if not valid

        // Create the dictionary of words to ignore
        // You do not need to change this code.
        DictionaryADT<String> ignore = new BSTDictionary<String>();
        while (inIgnore.hasNext()) {
            try {
                ignore.insert(inIgnore.next().toLowerCase());
            } catch (DuplicateException e) {
                // if there is a duplicate, we'll just ignore it
            }
        }
        
        // Process the input file line by line
        while (in.hasNext()) {
            String line = in.nextLine();
            List<String> words = parseLine(line);

            for (String word : words) {
            	String ignoreWord = ignore.lookup(word.toLowerCase());
            	if (ignoreWord != null) { continue; }
            	KeyWord newWordEntry = new KeyWord(word);
            	try {
            		dictionary.insert(newWordEntry);
            	}
            	catch (DuplicateException e) {
            		newWordEntry = dictionary.lookup(newWordEntry);
            	}
            	newWordEntry.increment();
            }
        } // end while
        
        // Print out basic statistics about the input text
        // Number of unique words
        int numKeys = dictionary.size();
        System.out.printf("# keys: %d", numKeys);
        System.out.println("");
        // Average path length in the dictionary
        double avgPathLength = (double) dictionary.totalPathLength() / numKeys;
        System.out.printf("avg path length: %.3f", avgPathLength);
        System.out.println("");
        // Average linear path length were this stored in a singly linked list
        double linearPathLength = (double) (numKeys + 1) / 2;
        System.out.printf("linear avg path: %.1f", linearPathLength);
        System.out.println("");
        
        // Populate the priority queue based on number of occurrences
        ArrayHeap<KeyWord> priorityQueue = new ArrayHeap<KeyWord>(numKeys);
        Iterator<KeyWord> wordIter = dictionary.iterator();
        while (wordIter.hasNext()) { priorityQueue.insert(wordIter.next()); }
        
        // Insert the top priority words into a second dictionary to be
        // displayed in the word cloud.
        BSTDictionary<KeyWord> wordCloudList = new BSTDictionary<>();
        while (wordCloudList.size() < maxWords && !priorityQueue.isEmpty()) {
        	try {
        		wordCloudList.insert(priorityQueue.removeMax());
        	}
        	// There shouldn't be any duplicates in the priority queue, so no
        	// need to handle the exception
        	catch (DuplicateException e) {}
        }
        
        generateHtml(wordCloudList, out);

        // Close everything
        if (in != null) 
            in.close();
        if (inIgnore != null) 
            inIgnore.close();
        if (out != null) 
            out.close();
    }
    
    /**
     * Parses the given line into an array of words.
     * 
     * @param line a line of input to parse
     * @return a list of words extracted from the line of input in the order
     *         they appear in the line
     *         
     * DO NOT CHANGE THIS METHOD.
     */
    private static List<String> parseLine(String line) {
        String[] tokens = line.split("[ ]+");
        ArrayList<String> words = new ArrayList<String>();
        for (int i = 0; i < tokens.length; i++) {  // for each word
            
            // find index of first digit/letter
              boolean done = false; 
              int first = 0;
            String word = tokens[i];
            while (first < word.length() && !done) {
                if (Character.isDigit(word.charAt(first)) ||
                    Character.isLetter(word.charAt(first)))
                    done = true;
                else first++;
            }
            
            // find index of last digit/letter
            int last = word.length()-1;
            done = false;
            while (last > first && !done) {
                if (Character.isDigit(word.charAt(last)) ||
                        Character.isLetter(word.charAt(last)))
                        done = true;
                    else last--;
            }
            
            // trim from beginning and end of string so that is starts and
            // ends with a letter or digit
            word = word.substring(first, last+1);
  
            // make sure there is at least one letter in the word
            done = false;
            first = 0;
            while (first < word.length() && !done)
                if (Character.isLetter(word.charAt(first)))
                    done = true;
                else first++;           
            if (done)
                words.add(word);
        }
        
        return words;
    }
    
    /**
     * Generates the html file using the given list of words.  The html file
     * is printed to the provided PrintStream.
     * 
     * @param words a list of KeyWords
     * @param out the PrintStream to print the html file to
     * 
     * DO NOT CHANGE THIS METHOD
     */
    private static void generateHtml(DictionaryADT<KeyWord> words, 
                                     PrintStream out) {
           String[] colors = { 
         	"#CD5C5C",      //INDIANRED
		"#5F9EA0",      //CADETBLUE
	 	"#FA8072",      //SALMON
	 	"#E9967A",      //DARKSALMON
	 	"#FF69B4",      //HOTPINK
	 	"#FFA500",      //ORANGE
		"#B22222",	// FIREBRICK
	 	"#E6E6FA",      //LAVENDER
	 	"#8A2BE2",      //BLUEVIOLET
	 	"#6A5ACD",      //SLATEBLUE
	 	"#7FFF00",      //CHARTREUSE
	 	"#32CD32",      //LIMEGREEN
	 	"#228B22",      //FORESTGREEN
	 	"#66CDAA",      //MEDIUMAQUAMARINE
	 	"#00FFFF",      //CYAN
	 	"#1E90FF",      //DODGERBLUE
	 	"#FFE4C4",      //BISQUE
	 	"#8B4513",      //SADDLEBROWN
	 	"#F5F5DC",      //BEIGE
	 	"#C0C0C0"       //Silver       
	     };
           int initFontSize = 100;
   	   String fontFamily = "Cursive";
           
        // Print the header information including the styles
        out.println("<head>\n<title>Word Cloud</title>");
        out.println("<style type=\"text/css\">");
        out.println("body { font-family: "+fontFamily+" }");
        
        // Each style is of the form:
        // .styleN {
        //      font-size: X%;
        //      color: #YYYYYY;

        // }
        //  where N and X are integers and Y is a hexadecimal digit
        for (int i = 0; i < colors.length; i++)
            out.println(".style" + i + 
                    " {\n    font-size: " + (initFontSize + i*20)
                    + "%;\n    color: " + colors[i] + ";\n}");

        
        out.println("</style>\n</head>\n<body><p>");        
        
        // Find the minimum and maximum values in the collection of words
        int min = Integer.MAX_VALUE, max = 0;
        for (KeyWord word : words) {
            int occur = word.getOccurrences();
            if (occur > max)
                max = occur;
            if (occur < min)
                min = occur;
        }

        double slope = (colors.length - 1.0)/(max - min);
        
        for (KeyWord word : words) {
            out.print("<span class=\"style");
            
            // Determine the appropriate style for this value using
            // linear interpolation
            // y = slope *(x - min) (rounded to nearest integer)
            // where y = the style number
            // and x = number of occurrences
            int index = (int)Math.round(slope*(word.getOccurrences() - min));
            
            out.println(index + "\">" + word.getWord() + "</span>&nbsp;");
        }
        
        // Print the closing tags
        out.println("</p></body>\n</html>");
    }
 
    /**
     * Validates that the correct number of command line arguments are given.
     * Exits if the test fails.
     * @param args Array of command line arguments.
     */
    private static void validateCmdLineArgs(String[] args) {
    	if (args.length != 4) {
        	System.out.println("Four arguments required: inputFileName "
        			+ "outputFileName ignoreFileName maxWords");
        	System.exit(0);
        }
    }
    
    /**
     * Validates the input is a positive integer. Exits if the test fails
     * @param num The number of validate.
     */
    private static void validatePositiveInteger(int num) {
    	if (num < 1) {
    		System.out.println("Error: maxWords must be a positive integer");
    		System.exit(0);
    	}
    }
}
