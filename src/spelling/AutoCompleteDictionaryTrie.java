package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		String l_word= word.toLowerCase();

		char [] arr = l_word.toCharArray();

		TrieNode wordarr = new TrieNode();

		wordarr=root;

		for(int i=0;i<arr.length;i++)

		{
			if(wordarr.getValidNextCharacters().contains(arr[i]))
			{
				wordarr = wordarr.getChild(arr[i]);
			}
			else
				wordarr=wordarr.insert(arr[i]);
		}
		if(!(wordarr.endsWord()))
		{
			wordarr.setEndsWord(true);
			size++;
			return true;
		}
	    return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		String l_word= s.toLowerCase();

		char [] arr = l_word.toCharArray();
		TrieNode words = new TrieNode();
		words = root;
		for(int i = 0;i<arr.length;i++)
		{
			if(words.getValidNextCharacters().contains(arr[i]))
			{
			words = words.getChild(arr[i]);
			}
			else
				return false;
		}
		if(words.endsWord())
			return true;
		
		return false;
	}
	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 String s = prefix.toLowerCase();
    	 List<String> result = new LinkedList<String>();
    	 TrieNode node = root;
    	 for(int i =0 ; i<s.length(); i++)
    	 {
    		 char c = s.charAt(i);
    		 if(node.getValidNextCharacters().contains(c))
    		 {
    			 node = node.getChild(c);
    		 }
    		 else 
    			 return result;
    	 }
    	 int counter = 0;
    	 if(node.endsWord())
    	 {
    		 result.add(node.getText());
    		 counter++;
    	 }
    	 
    	 List<TrieNode> queue = new LinkedList<TrieNode>();
    	 List<Character> childchars = new LinkedList<Character>(node.getValidNextCharacters());
    	 
    	 for(int i =0 ; i < childchars.size(); i++)
    	 {
    		 char c  = childchars.get(i);
    		 queue.add(node.getChild(c));
    	 }
    	 while(!(queue.isEmpty()) && counter<numCompletions)
    	 {
    		 TrieNode node2 = queue.remove(0);
    		 if(node2.endsWord())
    		 {
    			 result.add(node2.getText());
    			 counter++;
    		 }
    		 List<Character> cs = new LinkedList<Character>(node2.getValidNextCharacters());
    		 for(int i=0;i<cs.size();i++)
    		 {
    			 char c = cs.get(i);
    			 queue.add(node2.getChild(c));
    		 }
    	 }
    	 return result;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}