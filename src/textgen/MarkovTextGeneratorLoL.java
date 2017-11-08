package textgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode<String>> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode<String>>();
		starter = "";
		rnGenerator = generator;
		
	}
	
	String text;
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		// TODO: (my addition)split sourceText into list of words from second word and add it to
		//       the linked list as and when you reach the word or put it in the already existing nodes.
		//       How is the question?
		// TODO: (my addition)write a firstWord() method to get the firstWord from sourceText
		text = sourceText;
		List<String> listOfText = null;
		ListNode<String> prevWord = null;
		int lot_size = 0;
		try{
			listOfText = splitWord(sourceText);
		starter = listOfText.get(0);
		prevWord = new ListNode<String>(starter);
		lot_size = listOfText.size();
		wordList.add(prevWord);
		int size = 1;
		int flag = 0;
		for(int i=1;i<(lot_size);i++)
			{
				ListNode<String> tempNode = new ListNode<String>(listOfText.get(i));
				int index = 0;
				
				for(int j=0;j<size;j++)
				{
					if((prevWord.getWord()).equals((wordList.get(j)).getWord()))
						{
						index++;
						flag = j;
						}
				}
				if(index == 0)
				{
						wordList.add(prevWord);
						prevWord.addNextWord(tempNode.getWord());
						size++;
				}
				else if(index>0)
				{
					(wordList.get(flag)).addNextWord(tempNode.getWord());
				}
				else
				{
					throw new IndexOutOfBoundsException();
				}
					prevWord = new ListNode<String>(tempNode.getWord());
			}
			ListNode<String> lastNode = new ListNode<String>(listOfText.get(lot_size-1));
			lastNode.addNextWord(starter);
			wordList.add(lastNode);
			prevWord.addNextWord(starter);
		
		}catch(NullPointerException | IndexOutOfBoundsException e)
		{
			System.out.println("Enter Text");
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		String currWord = starter;
		String output = "";
		output+=currWord;
		int i = 1;
		int index = 0;
		if(numWords == 0)
		{
			output = "";
		}
		try{
			while(i<numWords){
			for(int j=0;j<(wordList.size());j++)
			{
				if((((wordList.get(j)).getWord()).equalsIgnoreCase(currWord)))
				{
					index = j;
					break;
				}
			}
			String w = null;
			w = (wordList.get(index)).getRandomNextWord(rnGenerator);
			output += " "+w;
			currWord = w;
			i++;
			}
		}catch(IndexOutOfBoundsException e)
		{
			System.out.println("Enter some text");
		}
		
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode<String> n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		text = sourceText;
		List<String> listOfText = null;
		ListNode<String> prevWord = null;
		ListNode<String> lastNode = null;
		wordList = new LinkedList<ListNode<String>>();
		int lot_size = 0;
		try{
			listOfText = splitWord(sourceText);
			starter = listOfText.get(0);
			prevWord = new ListNode<String>(starter);
			lot_size = listOfText.size();
			wordList.add(prevWord);
			int size = 1;
			int flag = 0;
			for(int i=1;i<(lot_size);i++)
			{
				ListNode<String> tempNode = new ListNode<String>(listOfText.get(i));
				int index = 0;
				
				for(int j=0;j<size;j++)
				{
					if((prevWord.getWord()).equals((wordList.get(j)).getWord()))
						{
						index++;
						flag = j;
						}
				}
				if(index == 0)
				{
						wordList.add(prevWord);
						prevWord.addNextWord(tempNode.getWord());
						size++;
				}
				else if(index>0)
				{
					(wordList.get(flag)).addNextWord(tempNode.getWord());
				}
				else
				{
					throw new IndexOutOfBoundsException();
				}
					prevWord = new ListNode<String>(tempNode.getWord());
			}
			lastNode = new ListNode<String>(listOfText.get(lot_size-1));
			lastNode.addNextWord(starter);
			wordList.add(lastNode);
			prevWord.addNextWord(starter);
		
		}catch(NullPointerException | IndexOutOfBoundsException e)
		{
			System.out.println("Enter Text");
		}
	}
	
	
	// TODO: Add any private helper methods you need here.
	//Split the text into list of strings
	public List<String> splitWord(String text)
	{
	
		List<String> words = getTokens("[a-zA-Z.']+");
		LinkedList<String> listOfText = new LinkedList<String>(words);

		return listOfText;
	}
	
	public List<String> getTokens(String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		gen.train("");
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		
		
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. 
 * @param <E>*/
class ListNode<E>
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		//for(int i=0;i<nextWords.size();i++)
		int randomNumber = generator.nextInt(nextWords.size());
		
	    return nextWords.get(randomNumber);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
}


