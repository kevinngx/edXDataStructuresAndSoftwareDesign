import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/*
 * SD2x Homework #3
 * Implement the methods below according to the specification in the assignment description.
 * Please be sure not to change the method signatures!
 */
public class Analyzer {
	

	public static List<Sentence> readFile(String filename) {
		LinkedList<Sentence> inputSentences = new LinkedList<>();
		String line = null;
		try {
			int score;
			String text;
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int counter = 0;
			while ((line = bufferedReader.readLine()) != null) {
				if (Character.isDigit(line.toCharArray()[0])) {
					if (line.toCharArray().length == 1)
						continue;
					score = Character.getNumericValue(line.toCharArray()[0]);
					text = line.substring(2, line.length());
					if (score > 2)
						continue;
				} else if (Character.isDigit(line.toCharArray()[1])){
					if (line.toCharArray().length == 2)
						continue;
					score = -Character.getNumericValue(line.toCharArray()[1]);
					text = line.substring(3, line.length());
					if (score < -2)
						continue;
				} else {
					continue;
				}

				inputSentences.add(new Sentence(score, text));
				/*
				//Testing, remove this
				System.out.println("Score is: '" + inputSentences.get(counter).score + "'");
				System.out.println("Text is: \"" + inputSentences.get(counter).text + "\"");
				counter++;
				System.out.println("**********************************************************************************");
				*/
			}
			bufferedReader.close();
			return inputSentences;

		} catch(FileNotFoundException ex) {
			System.out.println("READFILE ERROR: Unable to open file '" + filename + "'");
			return inputSentences;
		} catch(IOException ex) {
			System.out.println("READFILE ERROR: Error reading file '" + filename + "'");
			return inputSentences;
		} catch (IndexOutOfBoundsException ex) {
			System.out.println("READFILE ERROR: Index out of bounds!");
			return inputSentences;
		} catch (NullPointerException ex) {
			System.out.println("READFILE ERROR: filename is null");
			return inputSentences;
		}
	}

	public static Set<Word> allWords(List<Sentence> sentences) {
		HashMap<String, Word> wordMap = new HashMap<>();
		HashSet<Word> wordSet = new HashSet<>();
		try {

			for (int i = 0; i < sentences.size(); i++) {
				for (String word : sentences.get(i).text.split(" ")) {
					if (!word.isEmpty()) {
						if (!Character.isLetter(word.toCharArray()[0])) {
							continue;
						}
					}
					String lowerCaseWord = word.toLowerCase();
					if (!wordMap.containsKey(lowerCaseWord))
						wordMap.put(lowerCaseWord, new Word(lowerCaseWord));
					wordMap.get(lowerCaseWord).increaseTotal(sentences.get(i).score);
				}
			}

			for (String key : wordMap.keySet()) {
				wordSet.add(wordMap.get(key));
			}

		} catch (NullPointerException ex) {
			System.out.println("ALLWORDS ERROR: Null Pointer Exception");
			return wordSet;
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.printf("ALLWORDS ERROR: Array Index out of Bounds Exception");
			return wordSet;
		}
		return wordSet;
	}

	public static Map<String, Double> calculateScores(Set<Word> words) {

		HashMap<String, Double> myMap = new HashMap<>();
		try {
			for (Word word : words)
				myMap.put(word.text, word.calculateScore());
		} catch (NullPointerException ex) {
			System.out.printf("CALCULATESCORES ERROR: Null Pointer Exception");
			return myMap;
		}
		return myMap;

	}

	public static double calculateSentenceScore(Map<String, Double> wordScores, String sentence) {
		double score = 0;
		try {
			for (String word : sentence.split(" ")) {
				if (wordScores.containsKey(word.toLowerCase())) {
					//Testing
					System.out.println("Word being analyzed is " + word.toLowerCase() + "\"");
					System.out.println("The score for '" + word.toLowerCase() + "' is: " + wordScores.get(word.toLowerCase()));
					System.out.println("*********************************************************************************************");
					score = score + wordScores.get(word.toLowerCase());
				}
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println("CALCULATESENTENCESCORE ERROR: Array Index out of Bounds Exception");
		} catch (NullPointerException ex) {
			System.out.println("CALCULATESENTENCESCORE ERROR: Null Pointer Exception");
		}

		
		return score;

	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please specify the name of the input file");
			System.exit(0);
		}
		String filename = args[0];
		System.out.print("Please enter a sentence: ");
		Scanner in = new Scanner(System.in);
		String sentence = in.nextLine();
		in.close();
		List<Sentence> sentences = Analyzer.readFile(filename);
		Set<Word> words = Analyzer.allWords(sentences);
		Map<String, Double> wordScores = Analyzer.calculateScores(words);
		double score = Analyzer.calculateSentenceScore(wordScores, sentence);
		System.out.println("The sentiment score is " + score);
	}
}
