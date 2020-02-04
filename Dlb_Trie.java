import java.util.*;
import java.io.*;
public class Dlb_Trie
{
	private Node root;
	private char terminator = '$';
	public Dlb_Trie(String fileName) throws Exception
	{
		BufferedReader infile = new BufferedReader(new FileReader(fileName));
		while(infile.ready())
		{
			String s = infile.readLine();
			put(s);
		}		
	}
	public void put(String key)
	{
		if(root==null)
		{
			//inserts first word
			Node curr = new Node(null,null,terminator);
			root = curr;
			for(int i = 0; i<key.length();i++)
			{
				Node next = new Node(null,null,terminator);
				curr.setChar(key.charAt(i));
				curr.setChild(next);
				curr = next;
			}
		}
		else
		{
			//not the first word
			put(root,key);
		}
	}
	private void put(Node rootNode,String key)
	{
		for(int i = 0; i<key.length(); i++)
		{
			Node sibling = searchSiblings(rootNode,key.charAt(i));
			if(sibling.getC() == key.charAt(i) && key.length()==1)
			{
				sibling = sibling.getChild();
				while(sibling.getSibling()!=null)
				{
					sibling = sibling.getSibling();
				}
				sibling.setSibling(new Node(null,null,terminator));
				return;
			}
			
			if(sibling.getSibling() == null && sibling.getC() != key.charAt(i))
			{
				Node newNode = new Node(null,null,key.charAt(i));
				sibling.setSibling(newNode);
				for(int j = i+1; j<key.length(); j++)
				{
					newNode.setChild(new Node(null,null,key.charAt(j)));
					newNode = newNode.getChild();
				}
				newNode.setChild(new Node(null,null,terminator));
				i = key.length();
			}
			else
			{
				put(sibling.getChild(),key.substring(i+1,key.length()));
				i=key.length();
			}
		}
	}
	
	public Node searchSiblings(Node curr,char c)
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
		String[] words = new String[5];
		Node curr = root;
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
		return words;
	}
	private int recSearch(Node curr,String word,int arrayIndex,String[] words)
	{
		if(arrayIndex == 5) return 5;
		if(curr != null && curr.getC() == terminator && curr.getSibling() == null)
		{
			words[arrayIndex++] = word;
			return arrayIndex;
		}
		if(curr != null && curr.getC() == terminator && curr.getChild() == null)
		{
			words[arrayIndex++] = word;
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
}