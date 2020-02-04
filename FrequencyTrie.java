import java.util.*;
import java.io.*;
public class FrequencyTrie
{
	private FreqNode root;
	private char terminator = '$';
	private int n;
	public FrequencyTrie()
	{
		
	}
	public void put(String key)
	{
		if(root==null)
		{
			//inserts first word
			FreqNode curr = new FreqNode();
			root = curr;
			for(int i = 0; i<key.length();i++)
			{
				FreqNode next = new FreqNode(null,null,terminator);
				curr.setChar(key.charAt(i));
				curr.setChild(next);
				curr = next;
			}
			curr.increment();
			n++;
		}
		else
		{
			put(root,key);
			n++;
		}
	}
	private void put(FreqNode rootNode,String key)
	{
		for(int i = 0; i<key.length(); i++)
		{
			FreqNode sibling = searchSiblings(rootNode,key.charAt(i));
			if(sibling.getC() == key.charAt(i) && key.length()==1)
				{
					sibling = sibling.getChild();
					while(sibling.getSibling()!=null && sibling.getC() != terminator)
					{
						sibling = sibling.getSibling();
					}
					if(sibling.getC() == terminator)
					{
						sibling.increment();
					}
					else
					{
						sibling.setSibling(new FreqNode(null,null,terminator));
						sibling.getSibling().increment();
					}
					return;
				}
			if(sibling.getSibling() == null && sibling.getC() != key.charAt(i))
			{
				FreqNode newNode = new FreqNode(null,null,key.charAt(i));
				sibling.setSibling(newNode);
				for(int j = i+1; j<key.length(); j++)
				{
					newNode.setChild(new FreqNode(null,null,key.charAt(j)));
					newNode = newNode.getChild();
				}
				newNode.setChild(new FreqNode(null,null,terminator));
				newNode.getChild().increment();
				i = key.length();
			}
			else
			{
				put(sibling.getChild(),key.substring(i+1,key.length()));
				i=key.length();	
			}
		}
	}
	public FreqNode searchSiblings(FreqNode curr,char c)
	{
		if(curr != null && curr.getC() == c) 
		{
			return curr;
		}
		while(curr.getSibling() != null && curr.getC() != c)
		{
			curr = curr.getSibling();
		}
		return curr;
	}
	
	public String[] search(String key)
	{
		String[] words = new String[n];
		FreqNode curr = root;
		for(int i = 0; i<key.length(); i++)
		{
			while(curr != null && curr.getC() != key.charAt(i))
			{
				curr = curr.getSibling();
			}
			if(curr == null) 
			{
				return words;
			}
			curr = curr.getChild();
		}
		recSearch(curr,key,0,words);
		return sortArr(words);
	}
	
	public String[] sortArr(String[] words)
	{
		int n = words.length;
		int unique = 0;
        for (int i = 0; i < n-1; i++)
		{			
            for (int j = 0; j < n-i-1; j++) 
			{
				if(words[j] != null && words[j+1] != null)
				{
					if(Integer.parseInt(words[j].substring(words[j].indexOf(terminator)+1))   <  Integer.parseInt(words[j+1].substring(words[j+1].indexOf(terminator)+1)))
					{ 
						
						String temp = words[j]; 
						words[j] = words[j+1]; 
						words[j+1] = temp; 
					} 
				}
			}
		}
		for(int i = 0; i<words.length; i++)
		{
			if(words[i] != null) words[i] = words[i].substring(0,words[i].indexOf(terminator));
		}
		return words;
	}
	
	private int recSearch(FreqNode curr,String word,int arrayIndex,String[] words)
	{
		if(arrayIndex == n) return n;
		if(curr != null && curr.getC() == terminator && curr.getSibling() == null)
		{
			words[arrayIndex++] = word+terminator+curr.getFreq();
			return arrayIndex;
		}
		if(curr != null && curr.getC() == terminator && curr.getChild() == null)
		{
			words[arrayIndex++] = word+terminator+curr.getFreq();
			return recSearch(curr.getSibling(),word,arrayIndex,words);
		}
		if(curr != null && curr.getChild() != null)
		{
			word = word + curr.getC();
			arrayIndex = recSearch(curr.getChild(),word,arrayIndex,words);
			word = word.substring(0,word.length()-1);
		}
		if(curr != null && curr.getSibling() != null)
		{
			arrayIndex = recSearch(curr.getSibling(),word,arrayIndex,words);
		}
		return arrayIndex;
	}
	/*
	public void printTrie()
	{
		//there
		System.out.println(root.getC() + " " + root.getFreq());
		System.out.println(root.getChild().getC() + " " + root.getChild().getFreq());
		System.out.println(root.getChild().getChild().getC() + " " + root.getChild().getChild().getFreq());
		System.out.println(root.getChild().getChild().getChild().getC() + " " + root.getChild().getChild().getChild().getFreq());
		System.out.println(root.getChild().getChild().getChild().getChild().getC() + " " + root.getChild().getChild().getChild().getChild().getFreq());
		System.out.println(root.getChild().getChild().getChild().getChild().getChild().getC() + " " + root.getChild().getChild().getChild().getChild().getChild().getFreq());
		
		//the
		System.out.println(root.getC() + " " + root.getFreq());
		System.out.println(root.getChild().getC() + " " + root.getChild().getFreq());
		System.out.println(root.getChild().getChild().getC() + " " + root.getChild().getChild().getFreq());
		System.out.println(root.getChild().getChild().getChild().getSibling().getC() + " " + root.getChild().getChild().getChild().getSibling().getFreq());
		
		//their
		System.out.println(root.getC() + " " + root.getFreq());
		System.out.println(root.getChild().getC() + " " + root.getChild().getFreq());
		System.out.println(root.getChild().getChild().getC() + " " + root.getChild().getChild().getFreq());
		System.out.println(root.getChild().getChild().getChild().getSibling().getSibling().getC() + " " + root.getChild().getChild().getChild().getSibling().getSibling().getFreq());
		System.out.println(root.getChild().getChild().getChild().getSibling().getSibling().getChild().getC() + " " + root.getChild().getChild().getChild().getSibling().getSibling().getChild().getFreq());
		System.out.println(root.getChild().getChild().getChild().getSibling().getSibling().getChild().getChild().getC() + " " + root.getChild().getChild().getChild().getSibling().getSibling().getChild().getChild().getFreq());
	}
	*/
}