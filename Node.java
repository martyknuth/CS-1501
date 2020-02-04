public class Node
{
	private Node child;
	private Node sibling;
	private char c;
	
	public Node()
	{
		setChild(null);
		setSibling(null);
	}
	
	public Node(Node child,Node sibling,char c)
	{
		setChild(child);
		setSibling(sibling);
		setChar(c);
	}
	public void setChild(Node child)
	{
		this.child = child;
	}
	public void setSibling(Node sibling)
	{
		this.sibling = sibling;
	}
	public void setChar(char c)
	{
		this.c = c;
	}
	public Node getChild()
	{
		return child;
	}
	public Node getSibling()
	{
		return sibling;
	}
	public char getC()
	{
		return c;
	}
}